package com.mmm.movienight.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.mmm.movienight.models.UserGAPIDetails;
import com.mmm.movienight.models.Users;
import com.mmm.movienight.repositories.UserRepository;
import com.mmm.movienight.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;

@RestController
public class GoogleAuthController {

    @Autowired
    UserService userService;

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
        Users user = userService.getActiveUser();
        String accessToken = tokenResponse.getAccessToken();
        String refreshtoken = tokenResponse.getRefreshToken();
        //TODO: Calculate time when token expires and save as DateTime instead?
        Long expiresInSeconds = tokenResponse.getExpiresInSeconds();
        user.setGapiDetails( new UserGAPIDetails( accessToken, refreshtoken, expiresInSeconds ) );
        userService.saveUser( user );

        //TODO: Return different status codes depending on Google server response
        return ResponseEntity.ok( "HttpStatus:" + HttpStatus.OK );
    }
}
