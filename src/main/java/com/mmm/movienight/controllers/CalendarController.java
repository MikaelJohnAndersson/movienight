package com.mmm.movienight.controllers;

import com.mmm.movienight.models.Users;
import com.mmm.movienight.services.GoogleService;
import com.mmm.movienight.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarController {

    @Autowired
    GoogleService googleService;


    @Autowired
    UserService userService;

    @GetMapping("/google/getevents")
    public ResponseEntity getEvents(@RequestParam(value = "calendarId", required = false, defaultValue = "primary") String calendarId){

        Users user = userService.getActiveUser();
        String accessToken = user.getGapiDetails().getAccesstoken();

        System.out.println(googleService.getEvents(calendarId, accessToken));

        //TODO: Return different status codes depending on Google server response
        return ResponseEntity.ok("HttpStatus:" + HttpStatus.OK);
    }


}
