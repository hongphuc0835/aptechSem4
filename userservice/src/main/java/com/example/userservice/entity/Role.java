package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude // Loại users khỏi hashCode và equals
    //ngăn Lombok đưa roles (trong User) và users (trong Role) vào tính toán hashCode() và equals(), phá vỡ vòng lặp.
    private Set<User> users = new HashSet<>();
}