package com.srivath.product.controllers;

import com.srivath.product.DTOs.FakeStoreProductDTO;
import com.srivath.product.DTOs.ProductDTO;
import com.srivath.product.DTOs.ProductPriceDTO;
import com.srivath.product.exceptions.ProductNotFoundException;
import com.srivath.product.models.Category;
import com.srivath.product.models.Product;
import com.srivath.product.services.FakeStoreProductService;
import com.srivath.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @GetMapping(value = {"/",""} )
    public ResponseEntity<List<Product>> getAllProducts()
    {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    //Add a new Product

    @PostMapping()
    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO productDTO)
    {
        if (productDTO.getCategory() == null || productDTO.getName() == null || productDTO.getPrice() == 0.0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Product addedProduct = productService.addProduct(productDTO);
        return new ResponseEntity<>(addedProduct, HttpStatus.OK);
    }
//    {
//      "name": "Red Kurta",
//      "description" : "Large size Red color Kurta",
//      "price" : 1800,
//      "image" : "kurtaimage",
//      "category" : "dress"
//    }


    //PATCH not supported. Use PUT instead
//    @PatchMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody ProductPriceDTO productPriceDTO)
//    {
//        Product updatedProduct = productService.updateProduct(id, productPriceDTO);
//        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable long id, @RequestBody ProductDTO productDTO)
    {
        Product replacedProduct = productService.replaceProduct(id, productDTO);
        return new ResponseEntity<>(replacedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable long id)
    {
        boolean result = productService.removeProduct(id);
        if (!result)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam("word") String word)
    {
        List<Product> products = productService.searchProduct(word);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
