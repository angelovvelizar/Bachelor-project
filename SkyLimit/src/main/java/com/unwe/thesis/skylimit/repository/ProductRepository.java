package com.unwe.thesis.skylimit.repository;

import com.unwe.thesis.skylimit.model.entity.CategoryEntity;
import com.unwe.thesis.skylimit.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    boolean existsByName(String name);

    List<ProductEntity> findAllByCategory(CategoryEntity category);
}
