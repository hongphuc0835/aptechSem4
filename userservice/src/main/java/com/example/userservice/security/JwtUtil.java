package com.example.userservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Sửa generateToken để nhận Set<String> roles thay vì String role
    public String generateToken(String email, Set<String> roles, Long userId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles) // Lưu Set<String> roles thay vì một role duy nhất
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Giải mã token và lấy email
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Kiểm tra token xem có hợp lệ không
    public boolean validateToken(String token, String email) {
        return extractEmail(token).equals(email) && !isTokenExpired(token);
    }

    // Kiểm tra token đã hết hạn chưa
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Giải mã token và lấy Claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Lấy Set<String> roles từ token
    public Set<String> extractRoles(String token) {
        return extractAllClaims(token).get("roles", Set.class);
    }

    // Lấy userId từ token (nếu cần)
    public Long extractUserId(String token) {
        return extractAllClaims(token).get("userId", Long.class);
    }
}