package com.novmah.restaurantmanagement.mapper;

import com.novmah.restaurantmanagement.dto.OrderItemDto;
import com.novmah.restaurantmanagement.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = FoodMapper.class)
public interface OrderItemMapper {

    @Mapping(target = "foodDto", source = "food")
    OrderItemDto map(OrderItem orderItem);

    @Mapping(target = "food", source = "foodDto")
    OrderItem map(OrderItemDto orderItemDto);

}
