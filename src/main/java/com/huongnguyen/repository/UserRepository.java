package com.huongnguyen.repository;

import com.huongnguyen.dto.response.UserResponseDto;
import com.huongnguyen.entity.User;
import com.huongnguyen.dto.response.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT new com.huongnguyen.dto.response.UserResponseDto(u.firstName, u.lastName, u.email) FROM User u")
    List<UserResponseDto> findAllUsers();

    @Query("""
    select u.firstName as firstName,
        u.lastName as lastName,
        u.email as email
    from User u
    """)
    List<UserResponse> findAllUsersWithRoles();


    Optional<User> findById(Long id);
}
