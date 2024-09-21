package com.huongnguyen.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huongnguyen.dto.request.PermissionRequest;
import com.huongnguyen.dto.response.PermissionResponse;
import com.huongnguyen.entity.Permission;
import com.huongnguyen.exception.AppException;
import com.huongnguyen.exception.ErrorCode;
import com.huongnguyen.repository.PermissionRepository;
import com.huongnguyen.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("permissionServiceImpl")
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final ObjectMapper objectMapper;

    public PermissionServiceImpl(PermissionRepository permissionRepository, ObjectMapper objectMapper) {
        this.permissionRepository = permissionRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Permission create(PermissionRequest request) {
        Permission permission = objectMapper.convertValue(request, Permission.class);

        if (permissionRepository.findByName(request.name()).isPresent()){
            throw new AppException(ErrorCode.PERMISSION_EXISTS);
        } else {
            permission.setName(request.name());
            return permissionRepository.save(permission);
        }
    }
}
