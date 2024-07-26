package com.novmah.restaurantmanagement.service;

import com.novmah.restaurantmanagement.dto.MenuDto;

import java.util.List;

public interface MenuService {

    MenuDto getMenuById(Integer id);
    List<MenuDto> getAllMenus();

    MenuDto createMenu(MenuDto menuDto);
    MenuDto updateMenu(MenuDto menuDto);
    String deleteMenuById(Integer id);
    MenuDto addFoodToMenu(Integer menuId, Long foodId);
    MenuDto removeFoodFromMenu(Integer menuId, Long foodId);
}
