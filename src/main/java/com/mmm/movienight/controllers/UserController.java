package com.mmm.movienight.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.mmm.movienight.config.Views;
import com.mmm.movienight.models.User;
import com.mmm.movienight.repositories.UserRepository;
import com.mmm.movienight.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/user")
    public RedirectView addNewUser(@RequestParam("username") String username, @RequestParam("password") String password) {

        //Generating random ObjectId and encrypting password
        User user = new User(ObjectId.get(), username, passwordEncoder.encode(password), null);
        //Save user to db
        userRepository.save(user);

        return new RedirectView("/login");
    }

    @GetMapping("/user/authenticated")
    public ResponseEntity authenticated(){

        User user = userService.getActiveUser();
        return new ResponseEntity(user.isAuthenticated(), HttpStatus.OK);
    }

    //Return only usernames
    @JsonView(Views.Public.class)
    @GetMapping("user/all")
    public ResponseEntity getAllUsers(){
        List<User> allUsers = userRepository.findAll();
        if(!allUsers.isEmpty()){
            return new ResponseEntity(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}
