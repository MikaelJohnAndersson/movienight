package com.mmm.movienight.controllers;

import com.mmm.movienight.models.User;
import com.mmm.movienight.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/user/new")
    public ResponseEntity addNewUser( @RequestParam("username") String username, @RequestParam("password") String password) {

        //Generating random ObjectId and encrypting password
        User user = new User(ObjectId.get(), username, passwordEncoder.encode(password), null);
        //Save user to db
        userRepository.save(user);

        // TODO: status handling
        return ResponseEntity.ok( HttpStatus.OK );
    }

}