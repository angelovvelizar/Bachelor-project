package com.unwe.thesis.skylimit.service;

import com.unwe.thesis.skylimit.model.entity.CategoryEntity;
import com.unwe.thesis.skylimit.model.entity.enums.CategoryEnum;
import com.unwe.thesis.skylimit.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CategoryServiceImpl {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public void seedCategories() {
        if (categoryRepository.count() == 0) {
            CategoryEntity category1 = new CategoryEntity();
            category1.setCategory(CategoryEnum.SKY);

            CategoryEntity category2 = new CategoryEntity();
            category2.setCategory(CategoryEnum.LAND);

            CategoryEntity category3 = new CategoryEntity();
            category3.setCategory(CategoryEnum.WATER);

            CategoryEntity category4 = new CategoryEntity();
            category4.setCategory(CategoryEnum.CITY);

            this.categoryRepository.saveAll(Set.of(category1, category2, category3, category4));
        }
    }
}
