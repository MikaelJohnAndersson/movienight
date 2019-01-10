package com.mmm.movienight.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;

import com.mmm.movienight.models.Users;
import com.mmm.movienight.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GoogleService {

    @Autowired
    UserService userService;

    private final DateTime minDate = new DateTime( new Date() );

    public Events getEvents() {

        Users user = userService.getActiveUser();
        String accessToken = user.getGapiDetails().getAccesstoken();

        GoogleCredential credential = new GoogleCredential().setAccessToken( accessToken );
        Calendar calendar = new Calendar.Builder( new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential ).setApplicationName( "MOVIE_NIGHT" ).build();
        Events events = null;
        try {
            events = calendar.events().list( "primary" ).setMaxResults( 50 ).setTimeMin( minDate ).setOrderBy( "startTime" ).setSingleEvents( true ).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return events;
    }
}
