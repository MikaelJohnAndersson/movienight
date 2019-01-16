package com.mmm.movienight.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;

import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

@Service
public class GoogleService {

    private final DateTime minDate = new DateTime( new Date() );

    public Events getEvents(String calendarId, String accessToken) {

        GoogleCredential credential = new GoogleCredential().setAccessToken( accessToken );
        //TODO: Put Application name in application properties
        Calendar calendar = new Calendar.Builder( new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential ).setApplicationName( "MOVIE-NIGHT" ).build();
        Events events = null;
        try {
            events = calendar.events().list( calendarId ).setMaxResults( 50 ).setTimeMin( minDate ).setOrderBy( "startTime" ).setSingleEvents( true ).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return events;
    }

    public GoogleCredential getRefreshedCredentials(String refreshCode) throws IOException {

        //Loading client credentials
        String CLIENT_SECRET_FILE = ResourceUtils.getFile( "classpath:client_secret_210833549992-v1apmfej0r5na34nb3nfah0h3c99sgk4.apps.googleusercontent.com.json" ).toString();
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load( JacksonFactory.getDefaultInstance(), new FileReader( CLIENT_SECRET_FILE ) );

        try {
            GoogleTokenResponse response = new GoogleRefreshTokenRequest(
                    new NetHttpTransport(), JacksonFactory.getDefaultInstance(), refreshCode, clientSecrets.getDetails().getClientId(), clientSecrets.getDetails().getClientSecret() )
                    .execute();

            return new GoogleCredential().setAccessToken(response.getAccessToken()).setExpiresInSeconds(response.getExpiresInSeconds());
        }
        catch( Exception ex ){
            ex.printStackTrace();
            return null;
        }
    }
}
