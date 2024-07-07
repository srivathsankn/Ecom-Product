package com.srivath.product.services;

import com.srivath.product.models.Category;
import com.srivath.product.models.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public interface CategoryService {
    public List<Category> getAllCategories();

    public Category getCategoryById(long id);

    Category getCategoryByName(String category);
}
