package com.novmah.restaurantmanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novmah.restaurantmanagement.dto.request.OrderRequest;
import com.novmah.restaurantmanagement.dto.response.OrderMessage;
import com.novmah.restaurantmanagement.dto.response.OrderResponse;
import com.novmah.restaurantmanagement.entity.*;
import com.novmah.restaurantmanagement.entity.status.OrderItemStatus;
import com.novmah.restaurantmanagement.entity.status.OrderStatus;
import com.novmah.restaurantmanagement.entity.status.PaymentStatus;
import com.novmah.restaurantmanagement.entity.status.TableStatus;
import com.novmah.restaurantmanagement.exception.ResourceNotFoundException;
import com.novmah.restaurantmanagement.mapper.OrderItemMapper;
import com.novmah.restaurantmanagement.mapper.OrderMapper;
import com.novmah.restaurantmanagement.repository.FoodRepository;
import com.novmah.restaurantmanagement.repository.OrderRepository;
import com.novmah.restaurantmanagement.repository.TableRepository;
import com.novmah.restaurantmanagement.repository.UserRepository;
import com.novmah.restaurantmanagement.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final TableRepository tableRepository;
    private final RabbitTemplate rabbitTemplate;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderServiceImpl(OrderRepository orderRepository, FoodRepository foodRepository, UserRepository userRepository, TableRepository tableRepository, RabbitTemplate rabbitTemplate, OrderMapper orderMapper, OrderItemMapper orderItemMapper) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
        this.tableRepository = tableRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = orderMapper.map(orderRequest);

        Map<Long, Food> foodMap = foodRepository.findAllById(
                orderRequest.getOrderItemDtoList().stream()
                        .map(i -> i.getFoodDto().getId())
                        .collect(Collectors.toSet())
        ).stream().collect(Collectors.toMap(Food::getId, food -> food));

        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "user ID", orderRequest.getUserId()));

        DiningTable diningTable = tableRepository.findById(orderRequest.getTableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table", "table ID", orderRequest.getTableId().toString()));
        if (diningTable.getStatus() != TableStatus.AVAILABLE){
            throw new ResourceNotFoundException("Table not available");
        }
        diningTable.setStatus(TableStatus.RESERVED);
        tableRepository.save(diningTable);

        List<OrderItem> orderItems = orderRequest.getOrderItemDtoList().stream().map(orderItemMapper::map).peek(i -> {
            i.setOrder(order);
            i.setStatus(OrderItemStatus.PENDING);
            Food food = foodMap.get(i.getFood().getId());
            if (food == null) {
                throw new ResourceNotFoundException("Food", "food ID", i.getFood().getId().toString());
            }
            i.setFood(food);
        }).collect(Collectors.toList());

        BigDecimal sum = orderItems.stream()
                .map(i -> i.getFood().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setOrderItems(orderItems);
        order.setUser(user);
        order.setDiningTable(diningTable);
        order.setTotalPrice(sum);
        order.setStatus(OrderStatus.PLACED);
        order.setPaymentStatus(PaymentStatus.PENDING);

        orderRepository.save(order);
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setUserEmail(order.getUser().getEmail());
        orderMessage.setUserName(order.getUser().getName());
        orderMessage.setUserRole(order.getUser().getRoles());
        orderMessage.setTableNumber(order.getDiningTable().getNumber());
        orderMessage.setTotalPrice(order.getTotalPrice());
        orderMessage.setOrderItemDtoList(orderItems.stream().map(orderItemMapper::map).toList());
        rabbitTemplate.convertAndSend("order.exchange", "order.new", orderMessage);
        log.info("{}", orderMapper.map(order));

        return orderMapper.map(order);
    }


    @Override
    public OrderResponse updateOrderStatus(String id, OrderStatus orderStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "order ID", id));
        order.setStatus(orderStatus);
        orderRepository.save(order);
        return orderMapper.map(order);
    }

    @Override
    public OrderResponse updatePaymentStatus(String id, PaymentStatus paymentStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "order ID", id));
        order.setPaymentStatus(paymentStatus);
        orderRepository.save(order);
        return orderMapper.map(order);
    }

    @Override
    public String deleteOrderById(String id) {
        orderRepository.deleteById(id);
        return "delete successfully!!!";
    }

    @Override
    public OrderResponse getOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "order ID", id));
        return orderMapper.map(order);
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::map).toList();
    }

    @Override
    public List<OrderResponse> getOrderByStatus(OrderStatus orderStatus) {
        List<Order> orders = orderRepository.findByStatus(orderStatus);
        return orders.stream().map(orderMapper::map).toList();
    }

    @Override
    public List<OrderResponse> getOrderByPaymentStatus(PaymentStatus paymentStatus) {
//        List<Order> orders = orderRepository.findByPaymentStatus(paymentStatus);
//        return orders.stream().map(orderMapper::map).toList();
        return null;
    }

    @Override
    public List<OrderResponse> getOrderByUserId(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(orderMapper::map).toList();
    }
}
