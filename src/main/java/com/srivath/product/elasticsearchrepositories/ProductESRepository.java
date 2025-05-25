package com.srivath.product.elasticsearchrepositories;

import com.srivath.product.models.Product;
import com.srivath.product.utils.ElasticSearchUtils;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductESRepository extends ElasticsearchRepository<Product, Long> {
    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);
}
