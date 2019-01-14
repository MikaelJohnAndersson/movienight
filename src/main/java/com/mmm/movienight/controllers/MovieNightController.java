package com.mmm.movienight.controllers;

import com.mmm.movienight.models.MovieNights;
import com.mmm.movienight.repositories.MovieNightRepository;
import com.mmm.movienight.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MovieNightController {

    private final UserService userService;
    private final MovieNightRepository movieNightRepository;

    @Autowired
    public MovieNightController(UserService userService, MovieNightRepository movieNightRepository) {
        this.userService = userService;
        this.movieNightRepository = movieNightRepository;
    }

    @PostMapping("/movienights/create")
    public ResponseEntity newMovieNight(@RequestBody MovieNights movieNight){

        String admin = userService.getActiveUser().getUsername();
        movieNight.setAdmin(admin);

        movieNightRepository.save(movieNight);

        //TODO: Return different status codes depending on Google server response
        return ResponseEntity.ok("HttpStatus:" + HttpStatus.OK);
    }
}
