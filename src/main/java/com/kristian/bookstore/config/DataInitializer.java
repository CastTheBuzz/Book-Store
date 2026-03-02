package com.kristian.bookstore.config;

import com.kristian.bookstore.model.Category;
import com.kristian.bookstore.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {

        if (categoryRepository.count() == 0) {

            categoryRepository.save(new Category("Programming"));
            categoryRepository.save(new Category("Science"));
            categoryRepository.save(new Category("Finance"));
            categoryRepository.save(new Category("Fiction"));
        }
    }
}
