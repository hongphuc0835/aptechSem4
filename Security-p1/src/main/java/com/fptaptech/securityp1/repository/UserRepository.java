package com.fptaptech.securityp1.repository;

import com.fptaptech.securityp1.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    @EntityGraph(attributePaths = "roles")
    User findByUsername(String username);
}
