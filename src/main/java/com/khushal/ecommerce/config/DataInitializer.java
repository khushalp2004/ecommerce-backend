package com.khushal.ecommerce.config;

import com.khushal.ecommerce.model.Role;
import com.khushal.ecommerce.model.User;
import com.khushal.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.admin.email:admin@example.com}")
    private String adminEmail;

    @Value("${security.admin.password:admin123}")
    private String adminPassword;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.existsByEmail(adminEmail)) {
            return;
        }

        User admin = new User();
        admin.setEmail(adminEmail);
        admin.setFullName("Admin");
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRoles(Collections.singleton(Role.ADMIN));

        userRepository.save(admin);
    }
}
