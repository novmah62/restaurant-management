package com.novmah.restaurantmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto implements Serializable {

    @JsonIgnore
    private Integer id;

    private String name;

    private String description;

    @JsonProperty("foods")
    private List<FoodDto> foodDtoList;

}
