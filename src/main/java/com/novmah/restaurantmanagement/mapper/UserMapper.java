package com.novmah.restaurantmanagement.mapper;

import com.novmah.restaurantmanagement.dto.UserDto;
import com.novmah.restaurantmanagement.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto map(User user);

    User map(UserDto userDto);

}
