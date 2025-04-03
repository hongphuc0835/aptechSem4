package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerifyOtpRequestDTO {
    private String email;
    private String otp;
    private String newPassword;

}
