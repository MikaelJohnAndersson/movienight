package com.mmm.movienight.repositories;

import com.mmm.movienight.models.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
