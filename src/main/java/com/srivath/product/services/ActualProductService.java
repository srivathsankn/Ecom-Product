package com.srivath.product.services;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.srivath.product.DTOs.ProductDTO;
import com.srivath.product.elasticsearchrepositories.ProductESRepository;
import com.srivath.product.exceptions.ProductNotFoundException;
import com.srivath.product.models.Category;
import com.srivath.product.models.Product;
import com.srivath.product.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ActualProductService implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductESRepository productESRepository;


    public ActualProductService(ProductRepository productRepository, CategoryService categoryService, ProductESRepository productESRepository)
    {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        //this.elasticSearchClient = elasticSearchClient;
        this.productESRepository = productESRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
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
        Product savedProduct =  this.productRepository.save(product);
        this.productESRepository.save(savedProduct);
        return savedProduct;
    }

    @Override
    public Product replaceProduct(long id, ProductDTO productDTO) throws ProductNotFoundException {
        Product product = this.convertProductDTOToProduct(productDTO);
        product.setId(id);
        if (productRepository.findById(id).isPresent()) {
            Product updatedProduct = this.productRepository.save(product);
            this.productESRepository.save(updatedProduct);
            return updatedProduct;
        }
        else
        {
            throw new ProductNotFoundException("Product with id " + id + " not found. Cannot replace.");
        }
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
            product = this.getProductById(id);
            this.productRepository.delete(product);
            this.productESRepository.delete(product);
        }
        catch(ProductNotFoundException e)
        {
            return false;
        }
        return true;
    }

    @Override
    public List<Product> searchProduct(String query) {

//        SearchRequest searchRequest =  SearchRequest.of(searchQuery -> searchQuery.query(query));
//        SearchResponse<Product> searchResponse = elasticSearchClient
//                .search(s -> s.index("products").query(searchQuery, Product.class));

        return this.productESRepository.findByNameContainingOrDescriptionContaining(query, query);
    }
}
