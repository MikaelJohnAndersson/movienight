package com.mmm.movienight.controllers;

import com.mmm.movienight.services.GoogleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalenderController {

    @Autowired
    GoogleService googleService;

    @GetMapping("/getEvents")
    public ResponseEntity getEvents(){

        System.out.println(googleService.getEvents());

        //TODO: Return different status codes depending on Google server response
        return ResponseEntity.ok("HttpStatus:" + HttpStatus.OK);
    }


}
