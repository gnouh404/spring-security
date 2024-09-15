package com.javaguide.springframework.springsecurityjwt.controller.auth;

import com.javaguide.springframework.springsecurityjwt.dto.request.AuthenticationResquest;
import com.javaguide.springframework.springsecurityjwt.dto.request.RegisterRequest;
import com.javaguide.springframework.springsecurityjwt.dto.response.AuthenticationResponse;
import com.javaguide.springframework.springsecurityjwt.dto.response.RegisterResponse;
import com.javaguide.springframework.springsecurityjwt.service.AuthenticationService;
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
