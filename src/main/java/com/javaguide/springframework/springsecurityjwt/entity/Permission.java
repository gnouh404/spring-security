package com.javaguide.springframework.springsecurityjwt.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissions_seq_generator")
    @SequenceGenerator(name = "permissions_seq_generator", sequenceName = "permissions_seq", allocationSize = 1)
    private int id;

    @Column(name = "permission_name")
    private String name;

    public Permission(){}

    public Permission(String name){
        this.name = name;
    }
}
