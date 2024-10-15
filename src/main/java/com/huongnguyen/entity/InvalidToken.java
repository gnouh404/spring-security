package com.huongnguyen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "invalid_tokens")
@Getter
@Setter
@RequiredArgsConstructor
public class InvalidToken {

    @Id
    private String id;

    @Column(name = "expiry_time")
    private Instant expiryTime;
}
