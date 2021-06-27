package com.app.product.repository;

import com.app.product.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<Users,Long> {
    List<Users> findAll();
    List<Users> findByEmail(String email);
}
