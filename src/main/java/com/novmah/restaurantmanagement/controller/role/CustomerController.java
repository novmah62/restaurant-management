package com.novmah.restaurantmanagement.controller.role;

import com.novmah.restaurantmanagement.constant.ApiConstant;
import com.novmah.restaurantmanagement.dto.FoodDto;
import com.novmah.restaurantmanagement.dto.MenuDto;
import com.novmah.restaurantmanagement.dto.TableDto;
import com.novmah.restaurantmanagement.dto.UserDto;
import com.novmah.restaurantmanagement.dto.request.OrderRequest;
import com.novmah.restaurantmanagement.dto.response.OrderResponse;
import com.novmah.restaurantmanagement.entity.status.TableStatus;
import com.novmah.restaurantmanagement.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final AuthService authService;
    private final FoodService foodService;
    private final MenuService menuService;
    private final OrderService orderService;
    private final TableService tableService;
    private final UserService userService;

    public CustomerController(AuthService authService, FoodService foodService, MenuService menuService, OrderService orderService, TableService tableService, UserService userService) {
        this.authService = authService;
        this.foodService = foodService;
        this.menuService = menuService;
        this.orderService = orderService;
        this.tableService = tableService;
        this.userService = userService;
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(authService.getCurrentUser().getId(), userDto);
    }

    @DeleteMapping
    public String deleteUser() {
        return userService.deleteUserById(authService.getCurrentUser().getId());
    }

    @GetMapping
    public UserDto getUser() {
        return userService.getUserById(authService.getCurrentUser().getId());
    }

    @GetMapping("/food")
    public List<FoodDto> getAllFood(@RequestParam(value = "pageNumber", defaultValue = ApiConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                    @RequestParam(value = "pageSize", defaultValue = ApiConstant.PAGE_SIZE, required = false) Integer pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = ApiConstant.SORT_BY, required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = ApiConstant.SORT_DIR, required = false) String sortDir) {
        return foodService.getAllFood(pageNumber, pageSize, sortBy, sortDir);
    }

    @GetMapping("/food/search/{name}")
    public List<FoodDto> searchFoodByName(@PathVariable String name,
                                          @RequestParam(value = "pageNumber", defaultValue = ApiConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                          @RequestParam(value = "pageSize", defaultValue = ApiConstant.PAGE_SIZE, required = false) Integer pageSize,
                                          @RequestParam(value = "sortBy", defaultValue = ApiConstant.SORT_BY, required = false) String sortBy,
                                          @RequestParam(value = "sortDir", defaultValue = ApiConstant.SORT_DIR, required = false) String sortDir) {
        return foodService.searchFoodByName(name, pageNumber, pageSize, sortBy, sortDir);
    }

    @GetMapping("/food/price/{low}/{high}")
    public List<FoodDto> getFoodByPrice(@PathVariable BigDecimal low, @PathVariable BigDecimal high,
                                        @RequestParam(value = "pageNumber", defaultValue = ApiConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                        @RequestParam(value = "pageSize", defaultValue = ApiConstant.PAGE_SIZE, required = false) Integer pageSize,
                                        @RequestParam(value = "sortBy", defaultValue = ApiConstant.SORT_BY, required = false) String sortBy,
                                        @RequestParam(value = "sortDir", defaultValue = ApiConstant.SORT_DIR, required = false) String sortDir) {
        return foodService.getFoodByPrice(low, high, pageNumber, pageSize, sortBy, sortDir);
    }

    @GetMapping("/menu")
    public List<MenuDto> getAllMenus() {
        return menuService.getAllMenus();
    }

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @GetMapping("/order")
    public List<OrderResponse> getOrderByUserId() {


        return orderService.getOrderByUserId(authService.getCurrentUser().getId());
    }

    @GetMapping("/table")
    public List<TableDto> getAllTable() {
        return tableService.getAllTable();
    }

    @GetMapping("/table/status/{tableStatus}")
    public List<TableDto> getTablesByStatus(@PathVariable TableStatus tableStatus) {
        return tableService.getTablesByStatus(tableStatus);
    }

}
