package com.kristian.bookstore.config;

import com.kristian.bookstore.model.Category;
import com.kristian.bookstore.model.User;
import com.kristian.bookstore.repository.CategoryRepository;
import com.kristian.bookstore.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        // Categories
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("Programming"));
            categoryRepository.save(new Category("Science"));
            categoryRepository.save(new Category("Finance"));
            categoryRepository.save(new Category("Fiction"));
        }

        // Admin user
       User existingAdmin = userRepository.findByUsername("admin").orElse(null);

        if (existingAdmin != null) {
            userRepository.delete(existingAdmin);
        }

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole("ROLE_ADMIN");

        userRepository.save(admin);
    }
}