package com.example.userservice.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Lấy token từ header Authorization
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Bỏ "Bearer " để lấy token
            try {
                email = jwtUtil.extractEmail(token);
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token đã hết hạn");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token đã hết hạn");
                return;
            } catch (MalformedJwtException | SignatureException | UnsupportedJwtException e) {
                logger.warn("JWT Token không hợp lệ");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token không hợp lệ");
                return;
            }
        }

        // Nếu token hợp lệ và chưa có authentication trong SecurityContext
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Xác thực token
            if (jwtUtil.validateToken(token, email)) {
                // Lấy roles từ token
                Set<String> roles = jwtUtil.extractRoles(token);

                // Tạo danh sách authorities từ roles
                Set<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new) // Mỗi role là một authority
                        .collect(Collectors.toSet());

                // Tạo authentication token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Đặt authentication vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Tiếp tục chuỗi filter
        filterChain.doFilter(request, response);
    }
}