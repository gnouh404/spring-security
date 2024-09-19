package com.huongnguyen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
public class RolesPermissionsId implements Serializable {

    @Column(name= "role_id")
    private int roleId;

    @Column(name = "permission_id")
    private int permissionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolesPermissionsId other = (RolesPermissionsId) o;
        return Objects.equals(this.roleId, other.roleId) && Objects.equals(this.permissionId, other.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, permissionId);
    }
}
