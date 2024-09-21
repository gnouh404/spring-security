package com.huongnguyen.service;

import com.huongnguyen.dto.request.PermissionRequest;
import com.huongnguyen.dto.response.PermissionResponse;
import com.huongnguyen.entity.Permission;

public interface PermissionService {

    public Permission create(PermissionRequest request);
}
