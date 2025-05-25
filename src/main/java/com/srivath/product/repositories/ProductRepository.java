package com.srivath.product.repositories;

import com.srivath.product.models.Category;
import com.srivath.product.models.Product;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    List<Product> findAllByCategory(Category category);

//    Product save(Product product);

    List<Product> findAll();

    List<Product> findByNameOrDescription(String name, String description);


    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);
}
