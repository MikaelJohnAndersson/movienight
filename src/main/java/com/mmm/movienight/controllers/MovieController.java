package com.mmm.movienight.controllers;

import com.mmm.movienight.models.Movie;
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
        Movie result = restTemplate.getForObject(uri, Movie.class);

        System.out.println(result);

        if(result != null) {
            movieRepository.save(result);
        }

        //TODO: Return different status codes depending on Google server response
        return ResponseEntity.ok("HttpStatus:" + HttpStatus.OK);
    }

    @GetMapping("/omdb/movies")
    public ResponseEntity getMovieSearch(@RequestParam("search") String search){
        final String uri = "http://www.omdbapi.com/?apikey=" + omdbkey + "&s=" + search;
        RestTemplate restTemplate = new RestTemplate();
        Movies.MovieSearch result = restTemplate.getForObject(uri, Movies.MovieSearch.class);

        //TODO: Return different status codes depending on Google server response
        return new ResponseEntity(result, HttpStatus.OK);
    }


}
