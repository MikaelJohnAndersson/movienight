package com.mmm.movienight.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;

import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Service;

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
}
