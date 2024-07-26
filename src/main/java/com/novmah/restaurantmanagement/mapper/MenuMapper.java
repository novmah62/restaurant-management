package com.novmah.restaurantmanagement.mapper;

import com.novmah.restaurantmanagement.dto.MenuDto;
import com.novmah.restaurantmanagement.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = FoodMapper.class)
public interface MenuMapper {

    @Mapping(target = "foodDtoList", source = "foods")
    MenuDto map(Menu menu);

    @Mapping(target = "foods", source = "foodDtoList")
    Menu map(MenuDto menuDto);

}
