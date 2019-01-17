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
    public MovieController( MovieRepository movieRepository ) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/omdb/movies/{title}")
    public ResponseEntity getMovie( @PathVariable("title") String title ) {
        final String uri = "http://www.omdbapi.com/?apikey=" + omdbkey + "&t=" + title;

        //Returning cached movie if exists
        Movie cached = movieRepository.findByTitleIgnoreCase( title );

        if (cached != null) {
            return new ResponseEntity( cached, HttpStatus.OK );
        }

        RestTemplate restTemplate = new RestTemplate();
        Movie result = restTemplate.getForObject( uri, Movie.class );

        if (result.getResponse().equals( "True" )) {
            movieRepository.save( result );
            return new ResponseEntity( result, HttpStatus.OK );
        }

        return new ResponseEntity( HttpStatus.NO_CONTENT );
    }

    @GetMapping("/omdb/movies")
    public ResponseEntity getMovieSearch( @RequestParam("search") String search ) {
        final String uri = "http://www.omdbapi.com/?apikey=" + omdbkey + "&s=" + search;
        RestTemplate restTemplate = new RestTemplate();
        Movie.MovieSearch result = restTemplate.getForObject( uri, Movie.MovieSearch.class );

        if(result.getSearchResult() != null){
            return new ResponseEntity( result, HttpStatus.OK );
        }

        return new ResponseEntity( HttpStatus.NO_CONTENT);
    }

}
