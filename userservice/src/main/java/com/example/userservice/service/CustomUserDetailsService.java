package com.example.userservice.service;

import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Lấy Optional<User> từ repository
        Optional<User> userOptional = userRepository.findByEmail(email);

        // Unwrap Optional để lấy User
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User không tồn tại với email: " + email));

        // Chuyển Set<Role> thành Set<SimpleGrantedAuthority>
        Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        // Trả về UserDetails (dùng User của Spring Security)
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                authorities
        );
    }
}