package com.srivath.product.services;

import com.srivath.product.DTOs.ProductDTO;
import com.srivath.product.exceptions.ProductNotFoundException;
import com.srivath.product.models.Category;
import com.srivath.product.models.Product;
import com.srivath.product.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ActualProductService implements ProductService{

    private ProductRepository productRepository;
    private CategoryService categoryService;

    public ActualProductService(ProductRepository productRepository, CategoryService categoryService)
    {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductbyId(long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if(!product.isEmpty())
            return product.get();

        throw new ProductNotFoundException("Product with id "+ id + " is not found");
    }

    @Override
    public List<Product> getAllProductsInCategory(String category) {
        Category category1  = this.categoryService.getCategoryByName(category);
        return this.productRepository.findAllByCategory(category1);
    }

    @Override
    public Product addProduct(ProductDTO productDTO) {
        Product product = this.convertProductDTOToProduct(productDTO);
        return this.productRepository.save(product);
    }

    @Override
    public Product replaceProduct(long id, ProductDTO productDTO) {
        Product product = this.convertProductDTOToProduct(productDTO);
        product.setId(id);
        return this.productRepository.save(product);
    }

    public Product convertProductDTOToProduct(ProductDTO productDTO)
    {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());

        Category category  = this.categoryService.getCategoryByName(productDTO.getCategory());
        product.setCategory(category);

        return product;
    }


    @Override
    public boolean removeProduct(long id) {
        Product product = null;
        try
        {
            product = this.getProductbyId(id);
            this.productRepository.delete(product);
        }
        catch(ProductNotFoundException e)
        {
            return false;
        }
        return true;
    }
}
