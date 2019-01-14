package com.mmm.movienight.controllers;

import com.mmm.movienight.models.Movies;
import com.mmm.movienight.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MovieController {

    private final MovieRepository movieRepository;

    @Value("${omdb.key}")
    private String omdbkey;

    @Autowired
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/omdb/movies/{title}")
    public ResponseEntity getMovie(@PathVariable("title") String title){
        final String uri = "http://www.omdbapi.com/?apikey=" + omdbkey + "&t=" + title;
        RestTemplate restTemplate = new RestTemplate();
        Movies result = restTemplate.getForObject(uri, Movies.class);

        System.out.println(result);

        if(result != null) {
            movieRepository.save(result);
        }

        //TODO: Return different status codes depending on Google server response
        return ResponseEntity.ok("HttpStatus:" + HttpStatus.OK);
    }


}
