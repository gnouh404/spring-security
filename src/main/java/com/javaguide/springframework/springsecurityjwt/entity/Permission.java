package com.javaguide.springframework.springsecurityjwt.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "permission_name")
    private String name;
}
