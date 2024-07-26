package com.novmah.restaurantmanagement.service;

import com.novmah.restaurantmanagement.dto.UserDto;
import com.novmah.restaurantmanagement.entity.User;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto updateUser(String id, UserDto userDto);
    String deleteUserById(String id);

    UserDto getUserById(String id);
    UserDto getUserByEmail(String email);
    List<UserDto> getAllUser();
    List<UserDto> getUserByNameContaining(String name);

}
