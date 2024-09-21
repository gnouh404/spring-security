package com.huongnguyen.dto.request;

import com.huongnguyen.validator.NotBlank;

import java.util.Set;

public record UserRequest(

        String firstName,
        String lastName,
        String email,
        String password
) {}
