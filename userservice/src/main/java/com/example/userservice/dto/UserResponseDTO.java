package com.example.userservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// Dữ liệu trả ra (response)
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;


}
