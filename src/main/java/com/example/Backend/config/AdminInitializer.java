package com.example.Backend.config;

import com.example.Backend.entity.User;
import com.example.Backend.repository.UserRepository;
import com.example.Backend.resource.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer {
@Autowired
private UserRepository userRepository;
@Autowired
private PasswordEncoder passwordEncoder;
    @PostConstruct
    public void initAdmin() {
        String adminEmail = "admin@gmail.com";

        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = User.builder()
                    .fullname("Admin")
                    .email(adminEmail)
                    .password(passwordEncoder.encode("admin123")) // Change this in production!
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);
            System.out.println("Default admin user created");
        }
    }
}
