package com.javaguide.springframework.springsecurityjwt.service;

import com.javaguide.springframework.springsecurityjwt.dto.request.RoleRequest;
import com.javaguide.springframework.springsecurityjwt.dto.response.RoleResponse;

public interface RoleService {

    public RoleResponse create(RoleRequest request);
}
