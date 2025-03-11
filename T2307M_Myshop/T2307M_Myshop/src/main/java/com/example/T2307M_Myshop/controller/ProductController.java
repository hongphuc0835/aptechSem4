package com.example.T2307M_Myshop.controller;

import org.springframework.ui.Model;
import com.example.T2307M_Myshop.entity.Product;
import com.example.T2307M_Myshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Product> productsList = productService.findAll();
        model.addAttribute("products", productsList);
        return "index"; // mapping tới view index.html (Thymeleaf)
    }

    // Hiển thị form thêm sản phẩm
    @GetMapping("/create")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "create";
    }

    @PostMapping("/create")
    public String createProduct(Product product) {
        productService.save(product);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);
        if (product != null) {
            model.addAttribute("product", product);
        }
        return "update";
    }

    @PostMapping("/update")
    public String updateProduct(@RequestParam("id") Long id, @ModelAttribute Product product) {
        productService.update(id,product);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/";
    }
}
