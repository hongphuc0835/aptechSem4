package com.example.userservice.repository;

import com.example.userservice.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findById(Long id);

    @EntityGraph(attributePaths = {"roles"})
    List<User> findAll();
}