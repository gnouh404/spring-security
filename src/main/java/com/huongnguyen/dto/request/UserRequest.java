package com.huongnguyen.dto.request;

import com.huongnguyen.validator.NotBlank;

import java.util.Set;

public record UserRequest(
        @NotBlank(message = "First name is required")
        String firstName,
        String lastName,
        String email,
        String password
) {}
