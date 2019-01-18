package com.mmm.movienight.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.model.Event;
import com.mmm.movienight.models.FullCalendarEventDto;
import com.mmm.movienight.models.UserGAPIDetails;
import com.mmm.movienight.models.User;
import com.mmm.movienight.services.GoogleService;
import com.mmm.movienight.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GoogleController {

    private final UserService userService;
    private final GoogleService googleService;

    @Autowired
    public GoogleController(UserService userService, GoogleService googleService) {
        this.userService = userService;
        this.googleService = googleService;
    }

    @PostMapping("/google/auth")
    public ResponseEntity storeAuthCode( @RequestBody String authcode, @RequestHeader("X-Requested-With") String xRequest ) throws IOException {

        if (xRequest == null) {
           return new ResponseEntity( HttpStatus.BAD_REQUEST );
        }

        //Loading client credentials
        String CLIENT_SECRET_FILE = ResourceUtils.getFile( "classpath:client_secret_210833549992-v1apmfej0r5na34nb3nfah0h3c99sgk4.apps.googleusercontent.com.json" ).toString();
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load( JacksonFactory.getDefaultInstance(), new FileReader( CLIENT_SECRET_FILE ) );

        String REDIRECT_URI = "http://localhost:8080";

        // Exchange auth code for access token
        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest( new NetHttpTransport(), JacksonFactory.getDefaultInstance(), "https://www.googleapis.com/oauth2/v4/token", clientSecrets.getDetails().getClientId(), clientSecrets.getDetails().getClientSecret(), authcode, REDIRECT_URI ).execute();

        //Getting token data and saving onto currently logged in user in db
        User user = userService.getActiveUser();
        String accessToken = tokenResponse.getAccessToken();
        String refreshToken = tokenResponse.getRefreshToken();
        Long expiresInSeconds = tokenResponse.getExpiresInSeconds();
        String expiresAt = LocalDateTime.now().plusSeconds(expiresInSeconds).toString();

        user.setGapiDetails( new UserGAPIDetails( accessToken, refreshToken, expiresAt ) );
        userService.saveUser( user );

        if(user.getGapiDetails() != null){
            return new ResponseEntity( HttpStatus.OK );
        }
        return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/google/getevents")
    public ResponseEntity getEventsForUsers() throws IOException{

        ArrayList<FullCalendarEventDto> events = new ArrayList<>();
        List<User> authorizedUsers = userService.findAuthorizedUsers();

        for (User user : authorizedUsers) {
            if(user.tokenIsExpired()){
                //Refreshing credentials and saving updated credentials to db
                userService.saveUser(googleService.refreshCredentials(user));
            }
            String accessToken = user.getGapiDetails().getAccesstoken();

            List<Event> userEvents = googleService.getEvents("primary", accessToken);
            //Serializing google events to correct format for frontend (FullCalendar)
            userEvents.forEach(event -> {
                String start = event.getStart().getDateTime().toString();
                String end = event.getEnd().getDateTime().toString();
                String title = event.getSummary();
                events.add(new FullCalendarEventDto(title, start, end));
            });
        }

        if (!events.isEmpty()){
            return new ResponseEntity(events, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
