package com.mmm.movienight.repositories;

import com.mmm.movienight.models.MovieNight;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieNightRepository extends MongoRepository<MovieNight, String> {
}
