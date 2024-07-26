package com.novmah.restaurantmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.novmah.restaurantmanagement.entity.status.OrderItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto implements Serializable {

    @JsonIgnore
    private Long id;

    private Integer quantity;

    private OrderItemStatus status;

    @JsonProperty("food")
    private FoodDto foodDto;

}
