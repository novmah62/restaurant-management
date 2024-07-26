package com.novmah.restaurantmanagement.service;

import com.novmah.restaurantmanagement.dto.UserDto;
import com.novmah.restaurantmanagement.dto.request.ChangePasswordRequest;
import com.novmah.restaurantmanagement.dto.request.LoginRequest;
import com.novmah.restaurantmanagement.dto.request.RefreshTokenRequest;
import com.novmah.restaurantmanagement.dto.request.RegisterRequest;
import com.novmah.restaurantmanagement.dto.response.ApiResponse;
import com.novmah.restaurantmanagement.dto.response.AuthenticationResponse;
import com.novmah.restaurantmanagement.entity.User;

public interface AuthService {

    ApiResponse<String> signup(RegisterRequest registerRequest);
    String verifyAccount(String token);
    AuthenticationResponse login(LoginRequest loginRequest);
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    String logout(RefreshTokenRequest refreshTokenRequest);
    String changePassword(ChangePasswordRequest changePasswordRequest);

    User getCurrentUser();

}
