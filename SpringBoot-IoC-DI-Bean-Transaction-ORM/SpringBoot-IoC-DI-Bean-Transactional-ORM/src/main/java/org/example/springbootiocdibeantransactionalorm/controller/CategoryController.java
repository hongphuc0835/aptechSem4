package org.example.springbootiocdibeantransactionalorm.controller;

import org.example.springbootiocdibeantransactionalorm.entity.Category;
import org.example.springbootiocdibeantransactionalorm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // List all categories
    @GetMapping({"", "/list"})
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category/list"; // Returns the view template for category list
    }

    // Show form for creating new category
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/form"; // Returns the view template for category form
    }

    // Show form for editing existing category
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        model.addAttribute("category", category);
        return "category/form"; // Uses same form for create and update
    }

    // Save or update category
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("category") Category category) {
        if (category.getId() == null) {
            // New category
            categoryService.createCategory(category);
        } else {
            // Update existing category
            categoryService.updateCategory(category.getId(), category);
        }
        return "redirect:/categories"; // Redirect to list after save
    }

    // Delete category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories"; // Redirect to list after delete
    }
}