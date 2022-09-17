package com.unwe.thesis.skylimit.repository;

import com.unwe.thesis.skylimit.model.entity.CategoryEntity;
import com.unwe.thesis.skylimit.model.entity.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    CategoryEntity findByCategory(CategoryEnum category);
}
