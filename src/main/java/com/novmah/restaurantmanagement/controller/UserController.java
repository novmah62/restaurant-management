package com.novmah.restaurantmanagement.controller;

import com.novmah.restaurantmanagement.dto.UserDto;
import com.novmah.restaurantmanagement.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable String id, @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable String id) {
        return userService.deleteUserById(id);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping
    public List<UserDto> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/name/{name}")
    public List<UserDto> getUserByNameContaining(@PathVariable String name) {
        return userService.getUserByNameContaining(name);
    }

}
