package com.app.product.repository;

import com.app.product.model.Tags;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TagsRepository extends MongoRepository<Tags, String> {
    List<Tags> findByTag(String tag);
    List<Tags> findByTagIn(List<String> tags);
    List<Tags> findByCountGreaterThan(int count);
}
