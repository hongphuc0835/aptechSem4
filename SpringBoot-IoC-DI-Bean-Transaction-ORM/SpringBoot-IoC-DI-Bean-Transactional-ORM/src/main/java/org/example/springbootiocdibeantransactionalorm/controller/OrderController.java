package org.example.springbootiocdibeantransactionalorm.controller;

import org.example.springbootiocdibeantransactionalorm.entity.Customer;
import org.example.springbootiocdibeantransactionalorm.entity.Order;
import org.example.springbootiocdibeantransactionalorm.entity.Product;
import org.example.springbootiocdibeantransactionalorm.service.CustomerService;
import org.example.springbootiocdibeantransactionalorm.service.OrderService;
import org.example.springbootiocdibeantransactionalorm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final CustomerService customerService;

    @Autowired
    public OrderController(OrderService orderService, ProductService productService, CustomerService customerService) {
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
    }

    // List all orders
    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "order/list";
    }

    // Show form for creating new order
    @GetMapping("/new")
    public String showOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("customer", customerService.getAllCustomers());
        model.addAttribute("availableProducts", productService.getAllProducts());
        return "order/form";
    }

    // Save new order
    @PostMapping
    public String saveOrder(@ModelAttribute("order") Order order,
                            @RequestParam("customerId") Long customerId,
                            @RequestParam("productsIds") List<Long> productIds) {
        Optional<Customer> custOpt = customerService.getCustomerById(customerId);
        custOpt.ifPresent(order::setCustomer);

        // Handle products
        if (productIds != null) {
            Set<Product> productList = new HashSet<>();
            for (Long pid : productIds) {
                productService.getProductById(pid).ifPresent(productList::add);
            }
            order.setProducts(productList);
        }

        // Không cần set date ở đây nữa, OrderService sẽ tự xử lý
        order.updateTotalPrice();
        orderService.createOrder(order);
        return "redirect:/orders";
    }

    // Show form for editing existing order
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
        model.addAttribute("order", order);
        model.addAttribute("customer", customerService.getAllCustomers());
        model.addAttribute("availableProducts", productService.getAllProducts());
        return "order/form";
    }

    // Update existing order
    @PostMapping("/{id}")
    public String updateOrder(@PathVariable Long id,
                              @ModelAttribute("order") Order order,
                              @RequestParam("customerId") Long customerId,
                              @RequestParam("productsIds") List<Long> productIds) {
        try {
            // Lấy order hiện tại từ database
            Order existingOrder = orderService.getOrderById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));

            // Cập nhật customer
            Optional<Customer> custOpt = customerService.getCustomerById(customerId);
            custOpt.ifPresent(existingOrder::setCustomer);

            // Cập nhật products
            if (productIds != null) {
                Set<Product> productList = new HashSet<>();
                for (Long pid : productIds) {
                    productService.getProductById(pid).ifPresent(productList::add);
                }
                existingOrder.setProducts(productList);
            }

            // Không cần set date ở đây nữa, OrderService sẽ tự xử lý
            orderService.updateOrder(id, existingOrder);
            return "redirect:/orders";
        } catch (RuntimeException e) {
            return "redirect:/orders/" + id + "/edit?error=updateFailed";
        }
    }

    // Delete order
    @GetMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return "redirect:/orders";
        } catch (RuntimeException e) {
            return "redirect:/orders?error=deleteFailed";
        }
    }
}