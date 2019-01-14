package com.mmm.movienight.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
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
            //TODO: Error handling
            // Without the `X-Requested-With` header, this request could be forged. Aborts.
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

        //TODO: Return different status codes depending on Google server response
        return ResponseEntity.ok( "HttpStatus:" + HttpStatus.OK );
    }

    @GetMapping("/google/getevents")
    public ResponseEntity getEvents(@RequestParam(value = "calendarId", required = false, defaultValue = "primary") String calendarId) throws IOException{

        User user = userService.getActiveUser();

        if(user.tokenIsExpired()){
            GoogleCredential userCredentials = googleService.getRefreshedCredentials(user.getGapiDetails().getRefreshtoken());
            String newToken = userCredentials.getAccessToken();
            Long expiresInSeconds = userCredentials.getExpiresInSeconds();
            String expiresAt = LocalDateTime.now().plusSeconds(expiresInSeconds).toString();
            user.getGapiDetails().setAccesstoken(newToken);
            user.getGapiDetails().setExpiresAt(expiresAt);
            userService.saveUser(user);
        }

        String accessToken = user.getGapiDetails().getAccesstoken();

        System.out.println(googleService.getEvents(calendarId, accessToken));

        //TODO: Return different status codes depending on Google server response
        return ResponseEntity.ok("HttpStatus:" + HttpStatus.OK);
    }

}
