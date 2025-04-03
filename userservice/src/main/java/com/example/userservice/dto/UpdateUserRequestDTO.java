package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UpdateUserRequestDTO {
    private String email;
    private String name;
    private String phone;
    private String address;
    private Set<Long> roleIds;
}
