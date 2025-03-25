package org.example.springbootiocdibeantransactionalorm.service;

import org.example.springbootiocdibeantransactionalorm.entity.Order;
import org.example.springbootiocdibeantransactionalorm.entity.Product;
import org.example.springbootiocdibeantransactionalorm.repository.OrderRepository;
import org.example.springbootiocdibeantransactionalorm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;

    }

    public Order createOrder(Order order) {
        order.setDate(LocalDateTime.now());

        if (order.getProducts() != null && !order.getProducts().isEmpty()) {
            order.getProducts().forEach(product -> {
                if (product.getId() == null) {
                    throw new IllegalArgumentException("Product must be persisted before adding to order");
                }
            });

            //  Tính tổng tiền từ danh sách sản phẩm
            double totalPrice = order.getProducts().stream()
                    .mapToDouble(Product::getPrice)
                    .sum();
            order.setTotalPrice(totalPrice); //  Gán tổng tiền vào đơn hàng
        } else {
            order.setTotalPrice(0.0); // Nếu không có sản phẩm, tổng tiền = 0
        }

        return orderRepository.save(order); // Lưu vào database
    }


    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get order by ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Update an existing order
    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.setDate(LocalDateTime.now());
        order.setProducts(orderDetails.getProducts());

        return orderRepository.save(order);
    }

    // Delete an order
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderRepository.delete(order);
    }

}