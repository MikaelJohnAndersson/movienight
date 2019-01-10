package com.mmm.movienight.controllers;

import com.mmm.movienight.models.Movie;
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

    @GetMapping("/getMovie")
    public ResponseEntity getMovie(@RequestParam("id") String movieid){
        final String uri = "http://www.omdbapi.com/?apikey=" + apikey + "&i=" + movieid;
        RestTemplate restTemplate = new RestTemplate();
        Movie result = restTemplate.getForObject(uri, Movie.class);
        System.out.println(result);

        //TODO: Return different status codes depending on Google server response
        return ResponseEntity.ok("HttpStatus:" + HttpStatus.OK);
    }


}
