package com.app.product.repository;

import com.app.product.model.ProductTagMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductTagMappingRepository extends MongoRepository<ProductTagMapping, Long> {
    List<ProductTagMapping> findByProductNameAndTag(String productName, String tag);

    List<ProductTagMapping> findByTag(String tag);

    List<ProductTagMapping> findByProductName(String productName);

    void deleteByProductName(String productName);

}
