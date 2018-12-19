package com.mmm.movienight.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleAuthController {

    @PostMapping("/storeauthcode")
    public ResponseEntity storeAuthCode(@RequestBody String authcode){
        System.out.println(authcode);
        return ResponseEntity.ok("HttpStatus:" + HttpStatus.OK);
    }
}
