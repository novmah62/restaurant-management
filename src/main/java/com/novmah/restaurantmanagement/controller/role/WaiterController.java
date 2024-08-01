package com.novmah.restaurantmanagement.controller.role;

import com.novmah.restaurantmanagement.constant.ApiConstant;
import com.novmah.restaurantmanagement.dto.FoodDto;
import com.novmah.restaurantmanagement.dto.MenuDto;
import com.novmah.restaurantmanagement.dto.TableDto;
import com.novmah.restaurantmanagement.dto.request.OrderRequest;
import com.novmah.restaurantmanagement.dto.response.OrderResponse;
import com.novmah.restaurantmanagement.entity.status.OrderStatus;
import com.novmah.restaurantmanagement.entity.status.TableStatus;
import com.novmah.restaurantmanagement.service.FoodService;
import com.novmah.restaurantmanagement.service.MenuService;
import com.novmah.restaurantmanagement.service.OrderService;
import com.novmah.restaurantmanagement.service.TableService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/waiter")
public class WaiterController {

    private final TableService tableService;
    private final OrderService orderService;
    private final MenuService menuService;
    private final FoodService foodService;

    public WaiterController(TableService tableService, OrderService orderService, MenuService menuService, FoodService foodService) {
        this.tableService = tableService;
        this.orderService = orderService;
        this.menuService = menuService;
        this.foodService = foodService;
    }

    @GetMapping("/table")
    public List<TableDto> getAllTable() {
        return tableService.getAllTable();
    }

    @GetMapping("/table/status/{tableStatus}")
    public List<TableDto> getTablesByStatus(@PathVariable TableStatus tableStatus) {
        return tableService.getTablesByStatus(tableStatus);
    }

    @PatchMapping("/table/{id}/status/{status}")
    public TableDto updateTableStatus(@PathVariable Integer id, @PathVariable TableStatus status) {
        return tableService.updateTableStatus(id, status);
    }

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @PatchMapping("/order/{id}/status/{orderStatus}")
    public OrderResponse updateOrderStatus(@PathVariable String id, @PathVariable OrderStatus orderStatus) {
        return orderService.updateOrderStatus(id, orderStatus);
    }

    @GetMapping("/order/{id}")
    public OrderResponse getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/order/status/{orderStatus}")
    public List<OrderResponse> getOrderByStatus(@PathVariable OrderStatus orderStatus) {
        return orderService.getOrderByStatus(orderStatus);
    }

    @GetMapping("/order/user/{userId}")
    public List<OrderResponse> getOrderByUserId(@PathVariable String userId) {
        return orderService.getOrderByUserId(userId);
    }

    @GetMapping("/menu/{id}")
    public MenuDto getMenuById(@PathVariable Integer id) {
        return menuService.getMenuById(id);
    }

    @GetMapping("/menu")
    public List<MenuDto> getAllMenus() {
        return menuService.getAllMenus();
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


}
