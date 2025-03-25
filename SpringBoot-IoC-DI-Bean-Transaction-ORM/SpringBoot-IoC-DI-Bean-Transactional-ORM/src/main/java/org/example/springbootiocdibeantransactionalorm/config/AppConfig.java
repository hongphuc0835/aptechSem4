package org.example.springbootiocdibeantransactionalorm.config;

import org.example.springbootiocdibeantransactionalorm.entity.Category;
import org.example.springbootiocdibeantransactionalorm.entity.Product;
import org.example.springbootiocdibeantransactionalorm.repository.CategoryRepository;
import org.example.springbootiocdibeantransactionalorm.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class AppConfig {
//    @Bean
//    public CommandLineRunner dataLoader(CategoryRepository categoryRepository, ProductRepository productRepository) {
//        return args -> {
//            Category electronics = new Category("Electronics", "Các loại thiết bị ");
//            Category books = new Category("Books", "Sách giấy in");
//            categoryRepository.save(electronics);
//            categoryRepository.save(books);
//            productRepository.save(new Product("Iphone",12,"Iphone 16",electronics));
//        };
//
//    }
}
