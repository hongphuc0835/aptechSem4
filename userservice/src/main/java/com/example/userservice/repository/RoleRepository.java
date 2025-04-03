package com.example.userservice.repository;

import com.example.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findByNameIn(Set<String> names);
    Role findByName(String name);
}