package com.mmm.movienight.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;

@RestController
public class GoogleAuthController {

    @PostMapping("/storeauthcode")
    public ResponseEntity storeAuthCode(@RequestBody String authcode, @RequestHeader("X-Requested-With") String xRequest) throws IOException{

        if (xRequest == null) {
            //TODO: Error handling
            // Without the `X-Requested-With` header, this request could be forged. Aborts.
        }

        //Loading client credentials
        String CLIENT_SECRET_FILE = ResourceUtils.getFile("classpath:client_secret_210833549992-v1apmfej0r5na34nb3nfah0h3c99sgk4.apps.googleusercontent.com.json").toString();
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new FileReader(CLIENT_SECRET_FILE));

        String REDIRECT_URI = "http://localhost:8080";

        // Exchange auth code for access token
        GoogleTokenResponse tokenResponse =
                new GoogleAuthorizationCodeTokenRequest(
                        new NetHttpTransport(),
                        JacksonFactory.getDefaultInstance(),
                        "https://www.googleapis.com/oauth2/v4/token",
                        clientSecrets.getDetails().getClientId(),
                        clientSecrets.getDetails().getClientSecret(),
                        authcode,
                        REDIRECT_URI)
                        .execute();

        String accessToken = tokenResponse.getAccessToken();

        System.out.println("Access token: "  + accessToken);

        //TODO: Return different status codes depending on Google server response
        return ResponseEntity.ok("HttpStatus:" + HttpStatus.OK);
    }
}
