package com.novmah.restaurantmanagement.controller;

import com.novmah.restaurantmanagement.dto.MenuDto;
import com.novmah.restaurantmanagement.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/{id}")
    public MenuDto getMenuById(@PathVariable Integer id) {
        return menuService.getMenuById(id);
    }

    @GetMapping
    public List<MenuDto> getAllMenus() {
        return menuService.getAllMenus();
    }

    @PostMapping
    public MenuDto createMenu(@RequestBody MenuDto menuDto) {
        return menuService.createMenu(menuDto);
    }

    @PutMapping
    public MenuDto updateMenu(@RequestBody MenuDto menuDto) {
        return menuService.updateMenu(menuDto);
    }

    @DeleteMapping("/{id}")
    public String deleteMenuById(@PathVariable Integer id) {
        return menuService.deleteMenuById(id);
    }

    @PatchMapping("/add/{menuId}/foods/{foodId}")
    public MenuDto addFoodToMenu(@PathVariable Integer menuId, @PathVariable Long foodId) {
        return menuService.addFoodToMenu(menuId, foodId);
    }

    @PatchMapping("/remove/{menuId}/foods/{foodId}")
    public MenuDto removeFoodFromMenu(@PathVariable Integer menuId, @PathVariable Long foodId) {
        return menuService.removeFoodFromMenu(menuId, foodId);
    }

}
