package com.huongnguyen.dto.response;

import lombok.Getter;

@Getter
public class ApiResponse {

    private final int code;
    private final String message;

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
