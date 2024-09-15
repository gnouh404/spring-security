package com.javaguide.springframework.springsecurityjwt.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role {

    public Role(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "role_name")
    private String name;

    public Role() {

    }
}
