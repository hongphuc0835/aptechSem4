package com.example.userservice.repository;

import com.example.userservice.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //là một spring bean được tạo tự động và kế thưa từ JpaRepository và được Spring Container quản lý.

// nhiệm vụ cung cấp crud và truy vấn tùy chỉnh
public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByEmail(String email); // Tìm user theo email

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);
}
