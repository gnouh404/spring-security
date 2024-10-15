package com.huongnguyen.repository;

import com.huongnguyen.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {
}
