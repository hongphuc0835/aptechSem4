package com.example.userservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// JWT token response
public class AuthResponseDTO {
    private String token;


}
