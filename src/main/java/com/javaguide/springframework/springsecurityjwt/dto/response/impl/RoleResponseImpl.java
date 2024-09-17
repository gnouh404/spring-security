package com.javaguide.springframework.springsecurityjwt.dto.response.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javaguide.springframework.springsecurityjwt.dto.response.RoleResponse;

public class RoleResponseImpl implements RoleResponse {
    @JsonProperty("roleName")
    private String roleName;

    @JsonProperty("permissions")
    private String permissions;

    public RoleResponseImpl(String roleName, String permissions) {
        this.roleName = roleName;
        this.permissions = permissions;
    }

    @Override
    public String getRoleName() {
        return roleName;
    }

    @Override
    public String getPermissions() {
        return permissions;
    }
}
