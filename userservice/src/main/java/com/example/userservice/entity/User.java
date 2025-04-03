package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String passwordHash;
    private String phone;
    private String address;

    @ManyToMany
    @JoinTable(
            name = "user_roles", // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "user_id"), // Cột khóa ngoại liên kết với User
            inverseJoinColumns = @JoinColumn(name = "role_id") // Cột khóa ngoại liên kết với Role
    )
    private Set<Role> roles = new HashSet<>(); // Khởi tạo Set để tránh NullPointerException
}