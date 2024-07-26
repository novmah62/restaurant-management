package com.novmah.restaurantmanagement.mapper;

import com.novmah.restaurantmanagement.dto.TableDto;
import com.novmah.restaurantmanagement.entity.DiningTable;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TableMapper {

    TableDto map(DiningTable table);
    DiningTable map(TableDto tableDto);

}
