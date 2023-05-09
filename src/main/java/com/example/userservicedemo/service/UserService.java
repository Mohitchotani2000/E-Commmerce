package com.example.userservicedemo.service;

import com.example.userservicedemo.entity.User;
import com.example.userservicedemo.payload.*;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

/**
 *  Interface of UserService
 */
public interface UserService {
    UserDto registerUser(User user);
    List<UserDto> getAllUsers();
    UserDto getUserById(long userId);
    JWTAuthResponse login(LoginDto loginDto) throws JOSEException;
    TokenRefreshResponse refreshToken(TokenRefreshRequest request);
    void logoutUser(long userId);
}
