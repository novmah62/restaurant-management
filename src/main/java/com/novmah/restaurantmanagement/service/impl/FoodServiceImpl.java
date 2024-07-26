package com.novmah.restaurantmanagement.service.impl;

import com.novmah.restaurantmanagement.mapper.FoodMapper;
import com.novmah.restaurantmanagement.dto.FoodDto;
import com.novmah.restaurantmanagement.entity.Food;
import com.novmah.restaurantmanagement.exception.ResourceNotFoundException;
import com.novmah.restaurantmanagement.repository.FoodRepository;
import com.novmah.restaurantmanagement.service.FoodService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    public FoodServiceImpl(FoodRepository foodRepository, FoodMapper foodMapper) {
        this.foodRepository = foodRepository;
        this.foodMapper = foodMapper;
    }

    @Override
    @Cacheable(value = "foods", key = "#id")
    public FoodDto getFoodById(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "food ID", id.toString()));
        return foodMapper.map(food);
    }

    @Override
    @Cacheable(value = "foods", key = "{#pageNumber, #pageSize, #sortBy, #sortDir}")
    public List<FoodDto> getAllFood(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc"))
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Food> foodPage = foodRepository.findAll(pageable);
        List<Food> foods = foodPage.getContent();
        return foods.stream().map(foodMapper::map).toList();
    }

    @Override
    @Cacheable(value = "foods", key = "{#name, #pageNumber, #pageSize, #sortBy, #sortDir}")
    public List<FoodDto> searchFoodByName(String name, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc"))
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Food> foodPage = foodRepository.findByNameContaining(name, pageable);
        List<Food> foods = foodPage.getContent();
        return foods.stream().map(foodMapper::map).toList();
    }

    @Override
    @Cacheable(value = "foods", key = "{#low, #high, #pageNumber, #pageSize, #sortBy, #sortDir}")
    public List<FoodDto> getFoodByPrice(BigDecimal low, BigDecimal high, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc"))
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Food> foodPage = foodRepository.findByPriceBetween(low, high, pageable);
        List<Food> foods = foodPage.getContent();
        return foods.stream().map(foodMapper::map).toList();
    }

    @Override
    @CacheEvict(value = "foods", allEntries = true)
    public FoodDto addFood(FoodDto foodDto) {
        Food food = foodRepository.save(foodMapper.map(foodDto));
        return foodMapper.map(food);
    }

    @Override
    @CacheEvict(value = "foods", allEntries = true)
    public FoodDto updateFood(Long id, FoodDto foodDto) {
        if (!foodRepository.existsById(id))
            throw new ResourceNotFoundException("Food", "food ID", id.toString());
        Food food = foodRepository.save(Food.builder()
                .id(id)
                .name(foodDto.getName())
                .description(foodDto.getDescription())
                .image(foodDto.getImage())
                .price(foodDto.getPrice()).build()
        );
        return foodMapper.map(food);
    }

    @Override
    @CacheEvict(value = "foods", allEntries = true)
    public String deleteFoodById(Long id) {
        foodRepository.deleteById(id);
        return "delete successfully!!!";
    }
}
