package com.javaguide.springframework.springsecurityjwt.service;

import com.javaguide.springframework.springsecurityjwt.dto.request.AuthenticationResquest;
import com.javaguide.springframework.springsecurityjwt.dto.request.RegisterRequest;
import com.javaguide.springframework.springsecurityjwt.dto.response.AuthenticationResponse;
import com.javaguide.springframework.springsecurityjwt.dto.response.CustomUserDetails;
import com.javaguide.springframework.springsecurityjwt.dto.response.RegisterResponse;
import com.javaguide.springframework.springsecurityjwt.entity.Role;
import com.javaguide.springframework.springsecurityjwt.entity.User;
import com.javaguide.springframework.springsecurityjwt.exception.AppException;
import com.javaguide.springframework.springsecurityjwt.exception.ErrorCode;
import com.javaguide.springframework.springsecurityjwt.repository.RoleRepository;
import com.javaguide.springframework.springsecurityjwt.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RoleRepository roleRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
    }

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()){
            throw new AppException(ErrorCode.EMAIL_EXISTS);
        } else {
            Role role = roleRepository.findByName("USER");
            Set<Role> roles = new HashSet<>(Set.of(role));
            User user = User.builder()
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .active(false)
                    .roles(roles)
                    .build();
            userRepository.save(user);
            return new RegisterResponse("User registered successfully");
        }
    }

    public AuthenticationResponse login(AuthenticationResquest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (AuthenticationException e){
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        String jwtToken = jwtService.generateToken(new CustomUserDetails(user));
        return new AuthenticationResponse(jwtToken);
    }


}
