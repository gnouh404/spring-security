package com.huongnguyen.service.impl;

import com.huongnguyen.dto.request.AuthenticationResquest;
import com.huongnguyen.dto.request.LogoutRequest;
import com.huongnguyen.dto.request.RegisterRequest;
import com.huongnguyen.dto.response.AuthenticationResponse;
import com.huongnguyen.dto.response.RegisterResponse;
import com.huongnguyen.entity.InvalidToken;
import com.huongnguyen.entity.Role;
import com.huongnguyen.exception.AppException;
import com.huongnguyen.exception.ErrorCode;
import com.huongnguyen.repository.InvalidTokenRepository;
import com.huongnguyen.repository.RoleRepository;
import com.huongnguyen.service.AuthenticationService;
import com.huongnguyen.service.JwtService;
import com.huongnguyen.dto.response.CustomUserDetails;
import com.huongnguyen.entity.User;
import com.huongnguyen.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service("authServiceImpl")
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final InvalidTokenRepository invalidTokenRepository;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     JwtService jwtService,
                                     RoleRepository roleRepository,
                                     AuthenticationManager authenticationManager,
                                     InvalidTokenRepository invalidTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.invalidTokenRepository = invalidTokenRepository;
    }

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()){
            throw new AppException(ErrorCode.EMAIL_EXISTS);
        } else {
            Role role = roleRepository.findByName("USER")
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
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

    @Override
    public void logout(LogoutRequest request) {
        String jti = jwtService.extractJti(request.token());
        Date expiration = jwtService.extractExpiration(request.token());

        InvalidToken invalidToken = new InvalidToken();
        invalidToken.setJti(jti);
        invalidToken.setExpirationTime(expiration);

        invalidTokenRepository.save(invalidToken);
    }
}
