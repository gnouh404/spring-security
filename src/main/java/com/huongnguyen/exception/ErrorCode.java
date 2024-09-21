package com.huongnguyen.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    EMAIL_EXISTS(400, "Email already exists"),
    USER_NOT_FOUND(404, "User not found"),
    INVALID_CREDENTIALS(401, "Invalid credentials"),
    ACCESS_DENIED(403, "You do not have permission to access this resource"),
    PERMISSION_NOT_EXISTS(404, "One or more permissions does not exists"),
    PERMISSION_EXISTS(400, "Permission already exists"),
    ROLE_NOT_FOUND(404, "Role not found"),
    PERMISSION_NOT_FOUND(404, "Permission not found")
    ;

    private final String message;
    private final int code;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
