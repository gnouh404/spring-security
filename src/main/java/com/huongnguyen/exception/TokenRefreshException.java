package com.huongnguyen.exception;

import lombok.Getter;
import lombok.Setter;

@Getter

public class TokenRefreshException extends RuntimeException{

    private final String token;

    public TokenRefreshException(String token, String message) {
        super(message);
        this.token = token;
    }
}
