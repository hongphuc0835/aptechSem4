package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String address;

}
