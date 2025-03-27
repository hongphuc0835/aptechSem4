package org.example.springbootiocdibeantransactionalorm.repository;

import org.example.springbootiocdibeantransactionalorm.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Phương thức tải order kèm products
    @EntityGraph(attributePaths = {"products"})
    Optional<Order> findWithProductsById(Long id);

    // Phương thức tải tất cả orders kèm products và customer
    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.products LEFT JOIN FETCH o.customer")
    List<Order> findAllWithAssociations();

    // Phương thức tối ưu cho phân trang
    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.products p LEFT JOIN FETCH o.customer")
    List<Order> findAllWithProductsAndCustomer();
}