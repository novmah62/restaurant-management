package com.novmah.restaurantmanagement.dto.response;

import com.novmah.restaurantmanagement.dto.OrderItemDto;
import com.novmah.restaurantmanagement.entity.status.PaymentMethod;
import com.novmah.restaurantmanagement.entity.status.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse implements Serializable {

    private String userName;

    private Integer tableNumber;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private List<OrderItemDto> orderItemDtoList;

    private BigDecimal totalPrice;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
