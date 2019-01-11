package com.mmm.movienight.controllers;

import com.mmm.movienight.models.Movies;
import com.mmm.movienight.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MovieController {
    //TODO MOVE THIS TO A SAFE LOCATION 👀
    private String apikey = "9557cc1b";

    private final MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/omdb/movies")
    public ResponseEntity getMovie(@RequestParam("search") String search){
        final String uri = "http://www.omdbapi.com/?apikey=" + apikey + "&t=" + search;
        RestTemplate restTemplate = new RestTemplate();
        Movies result = restTemplate.getForObject(uri, Movies.class);

        if(result != null) {
            movieRepository.save(result);
        }

        //TODO: Return different status codes depending on Google server response
        return ResponseEntity.ok("HttpStatus:" + HttpStatus.OK);
    }


}
