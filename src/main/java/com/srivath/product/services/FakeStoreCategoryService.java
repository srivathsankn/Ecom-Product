package com.srivath.product.services;

import com.srivath.product.DTOs.FakeStoreProductDTO;
import com.srivath.product.models.Category;
import com.srivath.product.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FakeStoreCategoryService implements  CategoryService{

    private RestTemplate restTemplate;
    private ProductService productService;

    FakeStoreCategoryService(RestTemplate restTemplate, ProductService productService)
    {
        this.restTemplate = restTemplate;
        this.productService = productService;
    }

    @Override
    public List<Category> getAllCategories() {
        String[] categories = restTemplate.getForObject("https://fakestoreapi.com/products/categories",String[].class);
        List<Category> categoryList = new ArrayList<>();

        for(String category: categories)
        {
            categoryList.add(new Category(category));
        }
        return categoryList;
    }

    @Override
    public Category getCategoryById(long id) {
        String[] Categories = this.getAllCategories().stream().map(Category::getName).toArray(String[]::new);

        if (id < 0 || id >= Categories.length)
            return null;
        return new Category(Categories[(int) id]);
    }

    @Override
    public Category getCategoryByName(String category) {
        String[] categories = this.getAllCategories().stream().map(Category::getName).toArray(String[]::new);
        for (String cat: categories)
        {
            if (cat.equals(category))
                return new Category(category);
        }
        return null;
    }
}
