package com.example.userservice.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserCreateDTO {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Set<String> roles;
}
