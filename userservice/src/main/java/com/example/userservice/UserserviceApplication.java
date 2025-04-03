package com.example.userservice;

import com.example.userservice.entity.Role;
import com.example.userservice.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner initRoles(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("USER") == null) {
				Role userRole = new Role();
				userRole.setName("USER");
				roleRepository.save(userRole);
			}
			if (roleRepository.findByName("ADMIN") == null) {
				Role adminRole = new Role();
				adminRole.setName("ADMIN");
				roleRepository.save(adminRole);
			}
		};
	}
}