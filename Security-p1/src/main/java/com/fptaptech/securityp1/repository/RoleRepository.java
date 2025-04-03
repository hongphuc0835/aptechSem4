package com.fptaptech.securityp1.repository;


import com.fptaptech.securityp1.model.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @EntityGraph(attributePaths = "users")
    Role findByName(String name);
}
