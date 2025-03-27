package com.example.payroll_management.controller;
import com.example.payroll_management.entity.Employee;
import com.example.payroll_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", service.getAllEmployees());
        model.addAttribute("employee", new Employee()); // Form mặc định là create
        return "employee-list";
    }

    @PostMapping("/save")
    public String saveOrUpdateEmployee(@ModelAttribute Employee employee, Model model) {
        if (employee.getId() == null) {
            try {
                service.createEmployee(employee);
                model.addAttribute("successMessage", "User created successfully");
            } catch (RuntimeException e) {
                model.addAttribute("errorMessage", e.getMessage());
            }
        } else {
            service.updateEmployee(employee.getId(), employee);
            model.addAttribute("successMessage", "User updated successfully");
        }
        model.addAttribute("employees", service.getAllEmployees());
        model.addAttribute("employee", new Employee()); // Reset form
        return "employee-list";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {
        Employee emp = service.getAllEmployees().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);

        model.addAttribute("employee", emp);
        model.addAttribute("employees", service.getAllEmployees());
        return "employee-list";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, Model model) {
        service.deleteEmployee(id);
        model.addAttribute("successMessage", "User deleted successfully");
        model.addAttribute("employees", service.getAllEmployees());
        model.addAttribute("employee", new Employee()); // Reset form
        return "employee-list";
    }
}

