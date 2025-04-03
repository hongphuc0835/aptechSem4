package com.example.userservice.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String passwordHash; // Đảm bảo tên biến đúng


}
