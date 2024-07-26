package com.novmah.restaurantmanagement.repository;

import com.novmah.restaurantmanagement.entity.Order;
import com.novmah.restaurantmanagement.entity.status.OrderStatus;
import com.novmah.restaurantmanagement.entity.status.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUserId(String userId);
    List<Order> findByStatus(OrderStatus status);
//    List<Order> findByPaymentStatus(PaymentStatus paymentStatus);


}
