package com.mmm.movienight.repositories;

import com.mmm.movienight.models.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users, String> {
    Users findByUsername(String username);
}
