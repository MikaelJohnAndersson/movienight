package com.mmm.movienight.repositories;

import com.mmm.movienight.models.Movies;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movies, String> {
    Movies findByTitleIgnoreCase(String title);
}
