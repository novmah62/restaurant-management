package com.novmah.restaurantmanagement.repository;

import com.novmah.restaurantmanagement.entity.OrderItem;
import com.novmah.restaurantmanagement.entity.status.OrderItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByStatus(OrderItemStatus status);
}
