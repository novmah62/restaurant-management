package com.novmah.restaurantmanagement.service.impl;

import com.novmah.restaurantmanagement.mapper.MenuMapper;
import com.novmah.restaurantmanagement.dto.MenuDto;
import com.novmah.restaurantmanagement.entity.Food;
import com.novmah.restaurantmanagement.entity.Menu;
import com.novmah.restaurantmanagement.exception.ResourceNotFoundException;
import com.novmah.restaurantmanagement.repository.FoodRepository;
import com.novmah.restaurantmanagement.repository.MenuRepository;
import com.novmah.restaurantmanagement.service.MenuService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final FoodRepository foodRepository;
    private final MenuMapper menuMapper;

    public MenuServiceImpl(MenuRepository menuRepository, FoodRepository foodRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.foodRepository = foodRepository;
        this.menuMapper = menuMapper;
    }

    @Override
    @Cacheable(value = "menus", key = "#id")
    public MenuDto getMenuById(Integer id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "menu ID", id.toString()));
        return menuMapper.map(menu);
    }

    @Override
    @Cacheable(value = "menus", key = "'all'")
    public List<MenuDto> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream().map(menuMapper::map).toList();
    }

    @Override
    @CacheEvict(value = "menus", allEntries = true)
    public MenuDto createMenu(MenuDto menuDto) {
        Menu menu = menuRepository.save(menuMapper.map(menuDto));
        return menuMapper.map(menu);
    }

    @Override
    @CacheEvict(value = "menus", allEntries = true)
    public MenuDto updateMenu(MenuDto menuDto) {
        if (!menuRepository.existsById(menuDto.getId()))
            throw new ResourceNotFoundException("Menu", "menu ID", menuDto.getId().toString());
        Menu menu = menuRepository.save(menuMapper.map(menuDto));
        return menuMapper.map(menu);
    }

    @Override
    @CacheEvict(value = "menus", allEntries = true)
    public String deleteMenuById(Integer id) {
        menuRepository.deleteById(id);
        return "delete successfully";
    }

    @Override
    @CacheEvict(value = {"menus", "foods"}, allEntries = true)
    public MenuDto addFoodToMenu(Integer menuId, Long foodId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "menu ID", menuId.toString()));
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "food ID", foodId.toString()));
        List<Food> foods = menu.getFoods();
        foods.add(food);
        menu.setFoods(foods);
        menuRepository.save(menu);
        return menuMapper.map(menu);
    }

    @Override
    @CacheEvict(value = {"menus", "foods"}, allEntries = true)
    public MenuDto removeFoodFromMenu(Integer menuId, Long foodId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "menu ID", menuId.toString()));
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "food ID", foodId.toString()));
        List<Food> foods = menu.getFoods();
        foods.remove(food);
        menu.setFoods(foods);
        menuRepository.save(menu);
        return menuMapper.map(menu);
    }


}
