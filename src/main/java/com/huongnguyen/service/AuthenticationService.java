package com.huongnguyen.service;

import com.huongnguyen.dto.request.AuthenticationResquest;
import com.huongnguyen.dto.request.RegisterRequest;
import com.huongnguyen.dto.response.AuthenticationResponse;
import com.huongnguyen.dto.response.RegisterResponse;

public interface AuthenticationService {

    RegisterResponse register(RegisterRequest request);
    AuthenticationResponse login(AuthenticationResquest request);

}
