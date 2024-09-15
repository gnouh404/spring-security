package com.javaguide.springframework.springsecurityjwt.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_roles")
public class UsersRoles {

    @EmbeddedId
    private UsersRolesId id;

    public UsersRoles() {}

    public UsersRoles(UsersRolesId id) {
        this.id = id;
    }

    public int getUserId() {
        return id.getUserId();
    }

    public int getRoleId() {
        return id.getRoleId();
    }
}
