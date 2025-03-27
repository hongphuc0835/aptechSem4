package org.example.springbootiocdibeantransactionalorm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_detail",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Column(nullable = false)
    private Double totalPrice = 0.0; // Tổng tiền của đơn hàng

    //  Phương thức cập nhật tổng tiền dựa trên danh sách sản phẩm
    public void updateTotalPrice() {
        this.totalPrice = products.stream().mapToDouble(Product::getPrice).sum();
    }
}
