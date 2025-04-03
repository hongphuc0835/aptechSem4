package com.example.userservice.controller;

import com.example.userservice.dto.AuthRequestDTO;
import com.example.userservice.dto.AuthResponseDTO;
import com.example.userservice.dto.UserCreateDTO;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.security.JwtUtil;
import com.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        final UserDetails userDetails = userService.loadUserByUsername(authRequest.getEmail());
        final UserDTO userDTO = userService.getUserByEmail(authRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        final String jwt = jwtUtil.generateToken(
                userDetails,
                userDTO.getId(),
                userDTO.getRoles().stream().collect(Collectors.toList())
        );

        return ResponseEntity.ok(new AuthResponseDTO(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserCreateDTO userCreateDTO) {
        UserDTO registeredUser = userService.createUser(userCreateDTO);
        return ResponseEntity.ok(registeredUser);
    }
}