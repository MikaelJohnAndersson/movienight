package com.mmm.movienight.controllers;

import com.mmm.movienight.models.Users;
import com.mmm.movienight.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/addnewuser")
    public ResponseEntity addNewUser( @RequestParam("username") String username, @RequestParam("password") String password) {
        // TODO: encrypt password
        Users user = new Users( ObjectId.get(),username,password );
        // save user to db
        userRepository.save(user);
        // TODO: status handling
        return ResponseEntity.ok( HttpStatus.OK );
    }

}
