package com.srivath.product.services;

import com.srivath.product.models.Category;
import com.srivath.product.repositories.CategoryRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ActualCategoryService implements CategoryService{

    private final CategoryRepository categoryRepository;

    public ActualCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(long id) {
        return (this.categoryRepository.findById(id)).isEmpty()? null : this.categoryRepository.findById(id).get();
    }

    @Override
    public Category getCategoryByName(String category) {
        Optional<Category> categoryOpt = this.categoryRepository.findByName(category);
        Category categoryToReturn = null;
        if (categoryOpt.isPresent())
            categoryToReturn = categoryOpt.get();
        else
        {
           categoryToReturn = this.categoryRepository.save(new Category(category));
        }
        return categoryToReturn;
    }
}
