package com.unwe.thesis.skylimit.init;

import com.unwe.thesis.skylimit.repository.CategoryRepository;
import com.unwe.thesis.skylimit.service.CategoryServiceImpl;
import com.unwe.thesis.skylimit.service.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInit implements CommandLineRunner {
    private final UserServiceImpl userService;

    private final CategoryServiceImpl categoryService;



    public DataBaseInit(UserServiceImpl userService, CategoryServiceImpl categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.seedUsers();
        this.categoryService.seedCategories();
    }
}
