package com.huongnguyen.controller.auth;

import com.huongnguyen.dto.request.AuthenticationResquest;
import com.huongnguyen.dto.request.RegisterRequest;
import com.huongnguyen.dto.response.AuthenticationResponse;
import com.huongnguyen.dto.response.RegisterResponse;
import com.huongnguyen.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(@Qualifier("authServiceImpl") AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.
                ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationResquest request) {
        return ResponseEntity.
                ok(authenticationService.login(request));
    }
}
