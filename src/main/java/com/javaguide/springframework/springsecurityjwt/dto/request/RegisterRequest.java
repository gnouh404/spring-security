package com.javaguide.springframework.springsecurityjwt.dto.request;

public record RegisterRequest(String firstName, String lastName, String email, String password) {
}
