package com.mmm.movienight.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.mmm.movienight.models.MovieNight;
import com.mmm.movienight.models.User;
import com.mmm.movienight.repositories.MovieNightRepository;
import com.mmm.movienight.services.GoogleService;
import com.mmm.movienight.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MovieNightController {

    private final UserService userService;
    private final MovieNightRepository movieNightRepository;
    private final GoogleService googleService;

    @Autowired
    public MovieNightController(UserService userService, MovieNightRepository movieNightRepository, GoogleService googleService) {
        this.userService = userService;
        this.movieNightRepository = movieNightRepository;
        this.googleService = googleService;
    }

    @PostMapping("/movienight")
    public ResponseEntity newMovieNight(@RequestBody MovieNight movieNight) throws IOException{

        if(!userService.getActiveUser().isAuthenticated()){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String start = movieNight.getEvent().getStart();
        String end = movieNight.getEvent().getEnd();
        String title = movieNight.getEvent().getTitle();

        String admin = userService.getActiveUser().getUsername();
        movieNight.setAdmin(admin);

        List<User> allUsers = userService.findAuthorizedUsers();
        List<String> authorizedUsersUsernames = allUsers.stream().map(User::getUsername).collect(Collectors.toList());

        movieNight.setMembers(authorizedUsersUsernames);

        movieNightRepository.save(movieNight);

            allUsers.forEach(user -> {
                try {
                    if(user.tokenIsExpired()){
                        GoogleCredential userCredentials = googleService.getRefreshedCredentials(user.getGapiDetails().getRefreshtoken());
                        String newToken = userCredentials.getAccessToken();
                        Long expiresInSeconds = userCredentials.getExpiresInSeconds();
                        String expiresAt = LocalDateTime.now().plusSeconds(expiresInSeconds).toString();
                        user.getGapiDetails().setAccesstoken(newToken);
                        user.getGapiDetails().setExpiresAt(expiresAt);
                        userService.saveUser(user);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
                String accessToken = user.getGapiDetails().getAccesstoken();
                googleService.bookMovieNight(accessToken, start, end, title);
            });


        //TODO: Return different status codes depending on Google server response
        return new ResponseEntity(HttpStatus.OK);
    }
}
