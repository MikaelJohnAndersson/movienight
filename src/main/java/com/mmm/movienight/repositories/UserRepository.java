package com.mmm.movienight.repositories;

import com.fasterxml.jackson.annotation.JsonView;
import com.mmm.movienight.config.Views;
import com.mmm.movienight.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    //Filtering user on requesting all from db
    @Query(value = "{}", fields="{ '_id': 1, 'username': 1}")
    List<User> findAllAndFilterCredentials();

    User findByUsername(String username);
}
