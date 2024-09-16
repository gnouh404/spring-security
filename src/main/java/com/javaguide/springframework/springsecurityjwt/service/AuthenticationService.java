package com.javaguide.springframework.springsecurityjwt.service;

import com.javaguide.springframework.springsecurityjwt.dto.request.AuthenticationResquest;
import com.javaguide.springframework.springsecurityjwt.dto.request.RegisterRequest;
import com.javaguide.springframework.springsecurityjwt.dto.response.AuthenticationResponse;
import com.javaguide.springframework.springsecurityjwt.dto.response.RegisterResponse;

public interface AuthenticationService {

    RegisterResponse register(RegisterRequest request);
    AuthenticationResponse login(AuthenticationResquest request);

}
