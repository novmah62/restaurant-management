package com.novmah.restaurantmanagement.service;

import com.novmah.restaurantmanagement.entity.RefreshToken;

public interface RefreshTokenService {

    RefreshToken generateRefreshToken();
    void validateRefreshToken(String token);
    void deleteRefreshToken(String token);

}
