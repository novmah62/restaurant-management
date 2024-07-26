package com.novmah.restaurantmanagement.mapper;

import com.novmah.restaurantmanagement.dto.request.OrderRequest;
import com.novmah.restaurantmanagement.dto.response.OrderResponse;
import com.novmah.restaurantmanagement.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface OrderMapper {

    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "tableNumber", source = "diningTable.number")
    @Mapping(target = "orderItemDtoList", source = "orderItems")
    OrderResponse map(Order order);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "diningTable.id", source = "tableId")
    @Mapping(target = "orderItems", source = "orderItemDtoList")
    Order map(OrderRequest orderRequest);

}
