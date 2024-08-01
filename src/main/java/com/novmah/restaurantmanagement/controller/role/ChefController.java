package com.novmah.restaurantmanagement.controller.role;

import com.novmah.restaurantmanagement.constant.ApiConstant;
import com.novmah.restaurantmanagement.dto.FoodDto;
import com.novmah.restaurantmanagement.dto.MenuDto;
import com.novmah.restaurantmanagement.dto.OrderItemDto;
import com.novmah.restaurantmanagement.entity.status.OrderItemStatus;
import com.novmah.restaurantmanagement.service.FoodService;
import com.novmah.restaurantmanagement.service.MenuService;
import com.novmah.restaurantmanagement.service.OrderItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chef")
public class ChefController {

    private final OrderItemService orderItemService;
    private final FoodService foodService;
    private final MenuService menuService;

    public ChefController(OrderItemService orderItemService, FoodService foodService, MenuService menuService) {
        this.orderItemService = orderItemService;
        this.foodService = foodService;
        this.menuService = menuService;
    }

    @GetMapping("/orderItem")
    public List<OrderItemDto> getAllOrderItem() {
        return orderItemService.getAllOrderItem();
    }

    @GetMapping("/orderItem/status/{status}")
    public List<OrderItemDto> getOrderItemByStatus(@PathVariable OrderItemStatus status) {
        return orderItemService.getOrderItemByStatus(status);
    }

    @GetMapping("/orderItem/{id}")
    public OrderItemDto getOrderItemById(@PathVariable Long id) {
        return orderItemService.getOrderItemById(id);
    }

    @PatchMapping("/orderItem/{id}/status/{status}")
    public OrderItemDto updateOrderItemStatus(@PathVariable Long id, @PathVariable OrderItemStatus status) {
        return orderItemService.updateOrderItemStatus(id, status);
    }

    @GetMapping("/food/{id}")
    public FoodDto getFoodById(@PathVariable Long id) {
        return foodService.getFoodById(id);
    }

    @GetMapping("/food")
    public List<FoodDto> getAllFood(@RequestParam(value = "pageNumber", defaultValue = ApiConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                    @RequestParam(value = "pageSize", defaultValue = ApiConstant.PAGE_SIZE, required = false) Integer pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = ApiConstant.SORT_BY, required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = ApiConstant.SORT_DIR, required = false) String sortDir) {
        return foodService.getAllFood(pageNumber, pageSize, sortBy, sortDir);
    }

    @PostMapping("/food")
    public FoodDto addFood(@RequestBody FoodDto foodDto) {
        return foodService.addFood(foodDto);
    }

    @PutMapping("/food/{id}")
    public FoodDto updateFood(@PathVariable Long id,@RequestBody FoodDto foodDto) {
        return foodService.updateFood(id, foodDto);
    }

    @GetMapping("/menu/{id}")
    public MenuDto getMenuById(@PathVariable Integer id) {
        return menuService.getMenuById(id);
    }

    @GetMapping("/menu")
    public List<MenuDto> getAllMenus() {
        return menuService.getAllMenus();
    }

    @PostMapping("/menu")
    public MenuDto createMenu(@RequestBody MenuDto menuDto) {
        return menuService.createMenu(menuDto);
    }

    @PutMapping("/menu")
    public MenuDto updateMenu(@RequestBody MenuDto menuDto) {
        return menuService.updateMenu(menuDto);
    }

    @PatchMapping("/menu/add/{menuId}/foods/{foodId}")
    public MenuDto addFoodToMenu(@PathVariable Integer menuId, @PathVariable Long foodId) {
        return menuService.addFoodToMenu(menuId, foodId);
    }

    @PatchMapping("/menu/remove/{menuId}/foods/{foodId}")
    public MenuDto removeFoodFromMenu(@PathVariable Integer menuId, @PathVariable Long foodId) {
        return menuService.removeFoodFromMenu(menuId, foodId);
    }


}
