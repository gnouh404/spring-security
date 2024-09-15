package com.javaguide.springframework.springsecurityjwt.controller.auth;

import com.javaguide.springframework.springsecurityjwt.dto.request.AuthenticationResquest;
import com.javaguide.springframework.springsecurityjwt.dto.request.RegisterRequest;
import com.javaguide.springframework.springsecurityjwt.dto.response.AuthenticationResponse;
import com.javaguide.springframework.springsecurityjwt.dto.response.RegisterResponse;
import com.javaguide.springframework.springsecurityjwt.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.
                ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationResquest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            // Lấy các quyền của người dùng
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            // In ra các quyền
            authorities.forEach(authority -> {
                log.info("Quyền: " + authority.getAuthority());
            });
        }
        return ResponseEntity.
                ok(authenticationService.login(request));
    }
}
