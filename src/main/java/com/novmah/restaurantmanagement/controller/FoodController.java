package com.novmah.restaurantmanagement.controller;

import com.novmah.restaurantmanagement.constant.ApiConstant;
import com.novmah.restaurantmanagement.dto.FoodDto;
import com.novmah.restaurantmanagement.service.FoodService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/{id}")
    public FoodDto getFoodById(@PathVariable Long id) {
        return foodService.getFoodById(id);
    }

    @GetMapping
    public List<FoodDto> getAllFood(@RequestParam(value = "pageNumber", defaultValue = ApiConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                    @RequestParam(value = "pageSize", defaultValue = ApiConstant.PAGE_SIZE, required = false) Integer pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = ApiConstant.SORT_BY, required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = ApiConstant.SORT_DIR, required = false) String sortDir) {
        return foodService.getAllFood(pageNumber, pageSize, sortBy, sortDir);
    }

    @GetMapping("/search/{name}")
    public List<FoodDto> searchFoodByName(@PathVariable String name,
                                          @RequestParam(value = "pageNumber", defaultValue = ApiConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                          @RequestParam(value = "pageSize", defaultValue = ApiConstant.PAGE_SIZE, required = false) Integer pageSize,
                                          @RequestParam(value = "sortBy", defaultValue = ApiConstant.SORT_BY, required = false) String sortBy,
                                          @RequestParam(value = "sortDir", defaultValue = ApiConstant.SORT_DIR, required = false) String sortDir) {
        return foodService.searchFoodByName(name, pageNumber, pageSize, sortBy, sortDir);
    }

    @GetMapping("/price/{low}/{high}")
    public List<FoodDto> getFoodByPrice(@PathVariable BigDecimal low, @PathVariable BigDecimal high,
                                        @RequestParam(value = "pageNumber", defaultValue = ApiConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                        @RequestParam(value = "pageSize", defaultValue = ApiConstant.PAGE_SIZE, required = false) Integer pageSize,
                                        @RequestParam(value = "sortBy", defaultValue = ApiConstant.SORT_BY, required = false) String sortBy,
                                        @RequestParam(value = "sortDir", defaultValue = ApiConstant.SORT_DIR, required = false) String sortDir) {
        return foodService.getFoodByPrice(low, high, pageNumber, pageSize, sortBy, sortDir);
    }

    @PostMapping
    public FoodDto addFood(@RequestBody FoodDto foodDto) {
        return foodService.addFood(foodDto);
    }

    @PutMapping("/{id}")
    public FoodDto updateFood(@PathVariable Long id,@RequestBody FoodDto foodDto) {
        return foodService.updateFood(id, foodDto);
    }

    @DeleteMapping("/{id}")
    public String deleteFoodById(@PathVariable Long id) {
        return foodService.deleteFoodById(id);
    }


}
