package com.novmah.restaurantmanagement.service.impl;

import com.novmah.restaurantmanagement.mapper.OrderItemMapper;
import com.novmah.restaurantmanagement.dto.OrderItemDto;
import com.novmah.restaurantmanagement.entity.Food;
import com.novmah.restaurantmanagement.entity.OrderItem;
import com.novmah.restaurantmanagement.entity.status.OrderItemStatus;
import com.novmah.restaurantmanagement.exception.ResourceNotFoundException;
import com.novmah.restaurantmanagement.repository.FoodRepository;
import com.novmah.restaurantmanagement.repository.OrderItemRepository;
import com.novmah.restaurantmanagement.service.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final FoodRepository foodRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, FoodRepository foodRepository, OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.foodRepository = foodRepository;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public OrderItemDto addOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = orderItemMapper.map(orderItemDto);
        Food food = foodRepository.findById(orderItemDto.getFoodDto().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Food", "food ID", orderItem.getFood().getId().toString()));
        orderItem.setFood(food);
        orderItemRepository.save(orderItem);
        return orderItemMapper.map(orderItem);
    }

    @Override
    public OrderItemDto updateOrderItemStatus(Long id, OrderItemStatus orderItemStatus) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item", "order item ID", id.toString()));
        orderItem.setStatus(orderItemStatus);
        orderItemRepository.save(orderItem);
        return orderItemMapper.map(orderItem);
    }

    @Override
    public String removeOrderItemById(Long id) {
        orderItemRepository.deleteById(id);
        return "Delete successfully!!!";
    }

    @Override
    public OrderItemDto getOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item", "order item ID", id.toString()));
        return orderItemMapper.map(orderItem);
    }

    @Override
    public List<OrderItemDto> getOrderItemByStatus(OrderItemStatus orderItemStatus) {
        List<OrderItem> orderItems = orderItemRepository.findByStatus(orderItemStatus);
        return orderItems.stream().map(orderItemMapper::map).toList();
    }

    @Override
    public List<OrderItemDto> getAllOrderItem() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems.stream().map(orderItemMapper::map).toList();
    }

}
