package com.huongnguyen.service;

import com.huongnguyen.dto.request.AuthenticationResquest;
import com.huongnguyen.dto.request.LogoutRequest;
import com.huongnguyen.dto.request.RegisterRequest;
import com.huongnguyen.dto.request.TokenRefreshRequest;
import com.huongnguyen.dto.response.LoginResponse;
import com.huongnguyen.dto.response.RegisterResponse;
import com.huongnguyen.dto.response.TokenRefreshResponse;

public interface AuthenticationService {

    RegisterResponse register(RegisterRequest request);
    LoginResponse login(AuthenticationResquest request);
    TokenRefreshResponse refreshToken(TokenRefreshRequest request);
    void logout(LogoutRequest request);
}
