package com.huongnguyen.dto.request;

import java.util.List;

public record UserUpdateRequest(String firstName, String lastName, String email, String password, List<String> roles) {
}
