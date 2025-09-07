package com.srivath.product.services;

import com.srivath.product.DTOs.ProductDTO;
import com.srivath.product.exceptions.ProductNotFoundException;
import com.srivath.product.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    public List<Product> getAllProducts();

    public Product getProductById(long Id) throws ProductNotFoundException;

    public List<Product> getAllProductsInCategory(String category);

    public Product addProduct(ProductDTO productDTO);

    //public Product updateProduct(long id, ProductPriceDTO productPriceDTO);

    public Product replaceProduct(long id, ProductDTO productDTO) throws ProductNotFoundException;

    public boolean removeProduct(long id);

    public List<Product> searchProduct(String query);


}
