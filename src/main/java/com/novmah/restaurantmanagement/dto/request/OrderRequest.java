package com.novmah.restaurantmanagement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.novmah.restaurantmanagement.dto.OrderItemDto;
import com.novmah.restaurantmanagement.entity.status.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("tableId")
    private Integer tableId;

    private PaymentMethod paymentMethod;

    @JsonProperty("orderItems")
    private List<OrderItemDto> orderItemDtoList;

}
