package com.javaguide.springframework.springsecurityjwt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "roles")
public class Role {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq_generator")
    @SequenceGenerator(name = "roles_seq_generator", sequenceName = "roles_seq", allocationSize = 1)
    private int id;

    @Column(name = "role_name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    Set<Permission> permissions;

    public Role() {}
    public Role(String name) {
        this.name = name;
    }

}
