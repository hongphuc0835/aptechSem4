package com.example.payroll_management.service;


import com.example.payroll_management.entity.Employee;
import com.example.payroll_management.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee createEmployee(Employee employee) {
        if (repository.existsByName(employee.getName())) {
            throw new RuntimeException("User with name " + employee.getName() + " already exists.");
        }
        return repository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee newEmployee) {
        return repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setAge(newEmployee.getAge());
            employee.setSalary(newEmployee.getSalary());
            return repository.save(employee);
        }).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
}
