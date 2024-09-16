package com.javaguide.springframework.springsecurityjwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UsersRolesId implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;

    public int getUserId() {
        return userId;
    }

    public int getRoleId() {
        return roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersRolesId other = (UsersRolesId) o;
        return Objects.equals(this.userId, other.userId) && Objects.equals(this.roleId, other.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userId, this.roleId);
    }
}
