package com.mmm.movienight.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.mmm.movienight.config.Views;
import com.mmm.movienight.models.User;
import com.mmm.movienight.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/user/new")
    public ResponseEntity addNewUser( @RequestParam("username") String username, @RequestParam("password") String password) {

        //Generating random ObjectId and encrypting password
        User user = new User(ObjectId.get(), username, passwordEncoder.encode(password), null);
        //Save user to db
        userRepository.save(user);

        // TODO: status handling
        return ResponseEntity.ok( HttpStatus.OK );
    }

    @JsonView(Views.Public.class)
    @GetMapping("user/all")
    public ResponseEntity getAllUsers(){
        List<User> allUsers = userRepository.findAll();
        return new ResponseEntity(allUsers, HttpStatus.OK);
    }



}
