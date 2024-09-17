package com.javaguide.springframework.springsecurityjwt.service;

import com.javaguide.springframework.springsecurityjwt.dto.request.PermissionRequest;
import com.javaguide.springframework.springsecurityjwt.dto.response.PermissionResponse;

public interface PermissionService {

    public PermissionResponse create(PermissionRequest request);
}
