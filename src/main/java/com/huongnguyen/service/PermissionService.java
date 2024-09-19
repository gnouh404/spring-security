package com.huongnguyen.service;

import com.huongnguyen.dto.request.PermissionRequest;
import com.huongnguyen.dto.response.PermissionResponse;

public interface PermissionService {

    public PermissionResponse create(PermissionRequest request);
}
