package com.fptaptech.securityp1;

import com.fptaptech.securityp1.model.Role;
import com.fptaptech.securityp1.model.User;
import com.fptaptech.securityp1.repository.RoleRepository;
import com.fptaptech.securityp1.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class SecurityP1Application {

    public static void main(String[] args) {
        SpringApplication.run(SecurityP1Application.class, args);
    }

    @Bean
    public CommandLineRunner dataInitializer(RoleRepository roleRepository,
                                             UserRepository userRepository,
                                             PasswordEncoder passwordEncoder) {
        return args -> {
            Role roleUser = roleRepository.findByName("ROLE_USER");
            if (roleUser == null) {
                roleUser = new Role();
                roleUser.setName("ROLE_USER");
                roleRepository.save(roleUser);
            }
            if (userRepository.findByUsername("user") == null) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("password"));
                user.setRoles(Set.of(roleUser));
                userRepository.save(user);
            }
        };
    }

}
