package com.javaguide.springframework.springsecurityjwt.dto.request;

import java.util.Set;

public record RoleRequest(String name, Set<String> permissions) {
}
