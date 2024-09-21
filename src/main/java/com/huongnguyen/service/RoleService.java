package com.huongnguyen.service;

import com.huongnguyen.dto.request.AddPermissionToRole;
import com.huongnguyen.dto.request.RoleRequest;
import com.huongnguyen.dto.response.RoleResponse;
import com.huongnguyen.entity.Role;

import java.util.List;

public interface RoleService {

    public Role create(RoleRequest request);

    public List<RoleResponse> getAllRoleWithPermissions();

    public Role addPermissionToRole(AddPermissionToRole request);
}
