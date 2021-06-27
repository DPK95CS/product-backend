package com.app.product.repository;

import com.app.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByProductName(String productName);

    void deleteByProductName(String productName);

    List<Product> findByTagsIn(List<String> tagList);
}
