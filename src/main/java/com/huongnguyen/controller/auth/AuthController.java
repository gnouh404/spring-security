package com.huongnguyen.controller.auth;

import com.huongnguyen.dto.request.LogoutRequest;
import com.huongnguyen.dto.request.AuthenticationResquest;
import com.huongnguyen.dto.request.RegisterRequest;
import com.huongnguyen.dto.request.TokenRefreshRequest;
import com.huongnguyen.dto.response.*;
import com.huongnguyen.entity.RefreshToken;
import com.huongnguyen.service.AuthenticationService;
import com.huongnguyen.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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

    private final RefreshTokenService refreshTokenService;



    public AuthController(@Qualifier("authServiceImpl") AuthenticationService authenticationService,
                          @Qualifier("refreshTokenServiceImpl") RefreshTokenService refreshTokenService) {
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.
                ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthenticationResquest request) {
        return ResponseEntity.
                ok(authenticationService.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {

        return ResponseEntity
                .ok(authenticationService.refreshToken(request));
    }


}
