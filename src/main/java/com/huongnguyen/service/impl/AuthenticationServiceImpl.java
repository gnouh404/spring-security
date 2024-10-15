package com.huongnguyen.service.impl;

import com.huongnguyen.dto.request.AuthenticationResquest;
import com.huongnguyen.dto.request.LogoutRequest;
import com.huongnguyen.dto.request.RegisterRequest;
import com.huongnguyen.dto.request.TokenRefreshRequest;
import com.huongnguyen.dto.response.*;
import com.huongnguyen.entity.InvalidToken;
import com.huongnguyen.entity.RefreshToken;
import com.huongnguyen.entity.Role;
import com.huongnguyen.exception.AppException;
import com.huongnguyen.exception.ErrorCode;
import com.huongnguyen.exception.TokenRefreshException;
import com.huongnguyen.repository.InvalidTokenRepository;
import com.huongnguyen.repository.RoleRepository;
import com.huongnguyen.service.AuthenticationService;
import com.huongnguyen.service.JwtService;
import com.huongnguyen.entity.User;
import com.huongnguyen.repository.UserRepository;
import com.huongnguyen.service.RefreshTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
    private final RefreshTokenService refreshTokenService;
    private final InvalidTokenRepository invalidTokenRepo;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     JwtService jwtService,
                                     RoleRepository roleRepository,
                                     AuthenticationManager authenticationManager,
                                     @Qualifier("refreshTokenServiceImpl") RefreshTokenService refreshTokenService,
                                     InvalidTokenRepository invalidTokenRepo) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.invalidTokenRepo = invalidTokenRepo;
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

    public LoginResponse login(AuthenticationResquest request) {
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

        return new LoginResponse(
                jwtToken,
                "Bearer",
                refreshTokenService.createRefreshToken(user.getId()).getToken(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getName).toList()
        );

    }

    public TokenRefreshResponse refreshToken(TokenRefreshRequest request){
        String refreshToken = request.token();


        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyRefreshToken)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String jwtToken = jwtService.generateToken(new CustomUserDetails(user));
                    return new TokenRefreshResponse(jwtToken, refreshToken);
                })
                .orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token is not in database!"));
    }

    @Override
    public void logout(LogoutRequest request) {
        if (!jwtService.validateToken(request.token()) && jwtService.isTokenExpired(request.token())){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        String jti = jwtService.extractJti(request.token());
        Instant expiryTime = jwtService.extractExpiration(request.token()).toInstant();

        InvalidToken token = new InvalidToken();
        token.setId(jti);
        token.setExpiryTime(expiryTime);

        invalidTokenRepo.save(token);
    }

}
