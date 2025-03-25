package com.example.T2307M_Myshop.repository;

import com.example.T2307M_Myshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ProductRepository extends JpaRepository<Product, Long> {
}
