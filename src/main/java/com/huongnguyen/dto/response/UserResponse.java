package com.huongnguyen.dto.response;

import java.util.Set;

public interface UserResponse {
    String getFirstName();
    String getLastName();
    String getEmail();
    Set<String> getRoles();
}
