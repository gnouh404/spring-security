package com.huongnguyen.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles_permissions")
public class RolesPermissions {

    @EmbeddedId
    private RolesPermissionsId id;


    public RolesPermissions(){
        // no args constructor
    }

    public RolesPermissions(RolesPermissionsId id){
        this.id = id;
    }

    public int getRoleId(){ return id.getRoleId(); }

    public int getPermissionId(){ return id.getPermissionId(); }


}
