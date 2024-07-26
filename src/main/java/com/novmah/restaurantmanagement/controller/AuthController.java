package com.novmah.restaurantmanagement.controller;

import com.novmah.restaurantmanagement.dto.request.ChangePasswordRequest;
import com.novmah.restaurantmanagement.dto.request.LoginRequest;
import com.novmah.restaurantmanagement.dto.request.RefreshTokenRequest;
import com.novmah.restaurantmanagement.dto.request.RegisterRequest;
import com.novmah.restaurantmanagement.dto.response.ApiResponse;
import com.novmah.restaurantmanagement.dto.response.AuthenticationResponse;
import com.novmah.restaurantmanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse signup(@Valid @RequestBody RegisterRequest request) {
        return authService.signup(request);
    }

    @GetMapping("/accountVerification/{token}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<String> verifyAccount(@PathVariable String token) {
        return new ApiResponse<>(true, "", authService.verifyAccount(token));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh/token")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> logout(@Valid @RequestBody RefreshTokenRequest request) {
        return new ApiResponse<>(true, "", authService.logout(request));
    }

    @PatchMapping("/changePassword")
    @ResponseStatus(HttpStatus.OK)
    public String changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return authService.changePassword(changePasswordRequest);
    }

}
