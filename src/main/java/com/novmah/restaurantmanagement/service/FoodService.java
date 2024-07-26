package com.novmah.restaurantmanagement.service;

import com.novmah.restaurantmanagement.dto.FoodDto;

import java.math.BigDecimal;
import java.util.List;

public interface FoodService {

    FoodDto getFoodById(Long id);
    List<FoodDto> getAllFood(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    List<FoodDto> searchFoodByName(String name, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    List<FoodDto> getFoodByPrice(BigDecimal low, BigDecimal high, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    FoodDto addFood(FoodDto foodDto);
    FoodDto updateFood(Long id, FoodDto foodDto);
    String deleteFoodById(Long id);

}
