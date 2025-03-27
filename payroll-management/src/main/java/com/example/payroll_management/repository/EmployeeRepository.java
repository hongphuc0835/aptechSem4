package com.example.payroll_management.repository;

import com.example.payroll_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByName(String name);
}
