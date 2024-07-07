package com.srivath.product.controllers;

import com.srivath.product.models.Category;
import com.srivath.product.models.Product;
import com.srivath.product.services.CategoryService;
import com.srivath.product.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    private CategoryService categoryService;
    private ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService)
    {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories()
    {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<List<Product>> getAllProductsInCategory(@PathVariable("name") String category)
    {
        return new ResponseEntity<>(productService.getAllProductsInCategory(category),HttpStatus.OK);
    }

}
