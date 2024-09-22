package com.huongnguyen.dto.request;

public record UserRequest(

        String firstName,
        String lastName,
        String email,
        String password
) {}
