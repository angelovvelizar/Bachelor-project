package com.unwe.thesis.skylimit.service;

import com.unwe.thesis.skylimit.model.entity.CategoryEntity;
import com.unwe.thesis.skylimit.model.entity.ProductEntity;
import com.unwe.thesis.skylimit.model.service.ProductAddServiceModel;
import com.unwe.thesis.skylimit.repository.CategoryRepository;
import com.unwe.thesis.skylimit.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public boolean existsByName(String name){
        return this.productRepository.existsByName(name);
    }

    public void addProduct(ProductAddServiceModel productAddServiceModel){
        ProductEntity product = this.modelMapper.map(productAddServiceModel, ProductEntity.class);
        CategoryEntity category = this.categoryRepository.findByCategory(productAddServiceModel.getCategory());
        product.setCategory(category);
        this.productRepository.save(product);

    }
}
