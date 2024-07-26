package com.novmah.restaurantmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String image;

    private BigDecimal price;

}
