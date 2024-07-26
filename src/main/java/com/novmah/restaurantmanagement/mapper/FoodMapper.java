package com.novmah.restaurantmanagement.mapper;

import com.novmah.restaurantmanagement.dto.FoodDto;
import com.novmah.restaurantmanagement.entity.Food;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    FoodDto map(Food food);

    Food map(FoodDto foodDto);

}
