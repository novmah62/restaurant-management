package com.novmah.restaurantmanagement.service.impl;

import com.novmah.restaurantmanagement.mapper.UserMapper;
import com.novmah.restaurantmanagement.dto.UserDto;
import com.novmah.restaurantmanagement.entity.User;
import com.novmah.restaurantmanagement.entity.status.UserStatus;
import com.novmah.restaurantmanagement.exception.ResourceNotFoundException;
import com.novmah.restaurantmanagement.repository.UserRepository;
import com.novmah.restaurantmanagement.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.map(userDto);
        user.setEnabled(false);
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
        return userMapper.map(user);
    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        if (!userRepository.existsById(id))
            throw new ResourceNotFoundException("User", "user ID", id);
        User user = userRepository.save(User.builder()
                .id(id)
                .name(userDto.getName())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .status(userDto.getStatus()).build());
        return userMapper.map(user);
    }

    @Override
    public String deleteUserById(String id) {
        userRepository.deleteById(id);
        return "Delete successfully!!!";
    }

    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user ID", id));
        return userMapper.map(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return userMapper.map(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::map).toList();
    }

    @Override
    public List<UserDto> getUserByNameContaining(String name) {
        List<User> users = userRepository.findByNameContaining(name);
        return users.stream().map(userMapper::map).toList();
    }
}
