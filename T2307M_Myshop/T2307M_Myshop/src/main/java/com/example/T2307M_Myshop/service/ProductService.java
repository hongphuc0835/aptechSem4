package com.example.T2307M_Myshop.service;


import com.example.T2307M_Myshop.entity.Product;
import com.example.T2307M_Myshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    //Dependency Injection(DI)
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        return optional.orElse(null);
    }

    public  Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product productUpdate) {
        // Kiểm tra xem sản phẩm có tồn tại không
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            updatedProduct.setName(productUpdate.getName());
            updatedProduct.setPrice(productUpdate.getPrice());
            updatedProduct.setDescription(productUpdate.getDescription());

            return productRepository.save(updatedProduct);
        } else {
            throw new RuntimeException("Sản phẩm không tồn tại!");
        }
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
