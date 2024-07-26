package com.novmah.restaurantmanagement.controller;

import com.novmah.restaurantmanagement.dto.OrderItemDto;
import com.novmah.restaurantmanagement.entity.status.OrderItemStatus;
import com.novmah.restaurantmanagement.service.OrderItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orderItems")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public OrderItemDto addOrderItem(@RequestBody OrderItemDto orderItemDto) {
        return orderItemService.addOrderItem(orderItemDto);
    }

    @PatchMapping("/{id}/status/{status}")
    public OrderItemDto updateOrderItemStatus(@PathVariable Long id, @PathVariable OrderItemStatus status) {
        return orderItemService.updateOrderItemStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public String removeOrderItemById(@PathVariable Long id) {
        return orderItemService.removeOrderItemById(id);
    }

    @GetMapping("/{id}")
    public OrderItemDto getOrderItemById(@PathVariable Long id) {
        return orderItemService.getOrderItemById(id);
    }

    @GetMapping("/status/{status}")
    public List<OrderItemDto> getOrderItemByStatus(@PathVariable OrderItemStatus status) {
        return orderItemService.getOrderItemByStatus(status);
    }

    @GetMapping
    public List<OrderItemDto> getAllOrderItem() {
        return orderItemService.getAllOrderItem();
    }

}
