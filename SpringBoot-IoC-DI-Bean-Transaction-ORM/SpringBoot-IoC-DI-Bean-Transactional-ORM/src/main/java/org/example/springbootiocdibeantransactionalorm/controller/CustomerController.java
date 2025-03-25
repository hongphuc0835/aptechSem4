package org.example.springbootiocdibeantransactionalorm.controller;

import org.example.springbootiocdibeantransactionalorm.entity.Customer;
import org.example.springbootiocdibeantransactionalorm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // List all customers
    @GetMapping({"", "/list"})
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customer/list"; // Returns the view template for customer list
    }

    // Show form for creating new customer
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/form"; // Returns the view template for customer form
    }

    // Show form for editing existing customer
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", customer);
        return "customer/form"; // Uses same form for create and update
    }

    // Save or update customer
    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        if (customer.getId() == null) {
            // New customer
            customerService.createCustomer(customer);
        } else {
            // Update existing customer
            customerService.updateCustomer(customer.getId(), customer);
        }
        return "redirect:/customers"; // Redirect to list after save
    }

    // Delete customer
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers"; // Redirect to list after delete
    }
}