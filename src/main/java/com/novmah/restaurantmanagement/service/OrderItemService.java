package com.novmah.restaurantmanagement.service;

import com.novmah.restaurantmanagement.dto.OrderItemDto;
import com.novmah.restaurantmanagement.entity.status.OrderItemStatus;

import java.util.List;

public interface OrderItemService {

    OrderItemDto addOrderItem(OrderItemDto orderItemDto);
    OrderItemDto updateOrderItemStatus(Long id, OrderItemStatus orderItemStatus);
    String removeOrderItemById(Long id);

    OrderItemDto getOrderItemById(Long id);
    List<OrderItemDto> getOrderItemByStatus(OrderItemStatus orderItemStatus);
    List<OrderItemDto> getAllOrderItem();

}
