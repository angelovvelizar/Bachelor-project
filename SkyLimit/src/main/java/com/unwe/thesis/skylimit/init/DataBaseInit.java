package com.unwe.thesis.skylimit.init;

import com.unwe.thesis.skylimit.repository.CategoryRepository;
import com.unwe.thesis.skylimit.service.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInit implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final UserServiceImpl userService;

    public DataBaseInit(CategoryRepository categoryRepository, UserServiceImpl userService) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.seedUsers();
    }
}
