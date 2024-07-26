package com.novmah.restaurantmanagement.service;

import com.novmah.restaurantmanagement.dto.request.OrderRequest;
import com.novmah.restaurantmanagement.dto.response.OrderResponse;
import com.novmah.restaurantmanagement.entity.status.OrderStatus;
import com.novmah.restaurantmanagement.entity.status.PaymentStatus;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse updateOrderStatus(String id, OrderStatus orderStatus);
    OrderResponse updatePaymentStatus(String id, PaymentStatus paymentStatus);
    String deleteOrderById(String id);

    OrderResponse getOrderById(String id);
    List<OrderResponse> getAllOrder();
    List<OrderResponse> getOrderByStatus(OrderStatus orderStatus);
    List<OrderResponse> getOrderByPaymentStatus(PaymentStatus paymentStatus);

    List<OrderResponse> getOrderByUserId(String userId);

}
