package com.mmm.movienight.repositories;

import com.mmm.movienight.models.MovieNights;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieNightRepository extends MongoRepository<MovieNights, String> {
}
