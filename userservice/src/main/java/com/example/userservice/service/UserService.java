package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.dto.UserCreateDTO;
import com.example.userservice.dto.UserUpdateDTO;
import com.example.userservice.entity.Role;
import com.example.userservice.entity.User;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                authorities
        );
    }

    @Transactional
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setName(userCreateDTO.getName());
        user.setEmail(userCreateDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userCreateDTO.getPassword()));
        user.setPhone(userCreateDTO.getPhone());
        user.setAddress(userCreateDTO.getAddress());

        Set<Role> roles = roleRepository.findByNameIn(userCreateDTO.getRoles());
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDTO);
    }

    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::mapToDTO);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (userUpdateDTO.getName() != null) user.setName(userUpdateDTO.getName());
        if (userUpdateDTO.getEmail() != null) user.setEmail(userUpdateDTO.getEmail());
        if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(userUpdateDTO.getPassword()));
        }
        if (userUpdateDTO.getPhone() != null) user.setPhone(userUpdateDTO.getPhone());
        if (userUpdateDTO.getAddress() != null) user.setAddress(userUpdateDTO.getAddress());
        if (userUpdateDTO.getRoles() != null && !userUpdateDTO.getRoles().isEmpty()) {
            Set<Role> roles = roleRepository.findByNameIn(userUpdateDTO.getRoles());
            user.setRoles(roles);
        }

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        return dto;
    }
}