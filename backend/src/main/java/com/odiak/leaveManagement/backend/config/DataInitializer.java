package com.odiak.leaveManagement.backend.config;

import com.odiak.leaveManagement.backend.models.User;
import com.odiak.leaveManagement.backend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User user = new User();
                user.setName("Admin User");
                user.setEmail("admin@example.com");
                user.setPassword("admin123");
                user.setRole(User.Role.ADMIN);
                userRepository.save(user);
                System.out.println("Default user created: admin@example.com");
            }
        };
    }
}
