package com.huongnguyen.service;

import com.huongnguyen.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyRefreshToken(RefreshToken token);

    int deleteByUserId(Long userId);

    Optional<RefreshToken> findByToken(String token);
}
