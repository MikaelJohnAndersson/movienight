package com.mmm.movienight.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GoogleService {

    private final Instant instant = Instant.now().minus(7, ChronoUnit.DAYS);
    private final DateTime minDate = new DateTime(Date.from(instant));

    public List<Event> getEvents(String calendarId, String accessToken) {

        List<Event> events = new ArrayList<>();

        GoogleCredential credential = new GoogleCredential().setAccessToken( accessToken );
        //TODO: Put Application name in application properties
        Calendar service = new Calendar.Builder( new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential ).setApplicationName( "MOVIE-NIGHT" ).build();
        try {
            Events eventList = service.events().list( calendarId ).setMaxResults( 50 ).setTimeMin( minDate ).setOrderBy( "startTime" ).setSingleEvents( true ).execute();
            events = eventList.getItems();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return events;
    }

    public void bookMovieNight(String accessToken, String start, String end, String summary){

        Event event = new Event().setSummary(summary);

        DateTime startTime = new DateTime(start);
        EventDateTime eventStart = new EventDateTime().setDateTime(startTime);
        event.setStart(eventStart);

        DateTime endTime = new DateTime(end);
        EventDateTime eventEnd = new EventDateTime().setDateTime(endTime);
        event.setEnd(eventEnd);

        GoogleCredential credential = new GoogleCredential().setAccessToken( accessToken );
        //TODO: Put Application name in application properties
        Calendar service = new Calendar.Builder( new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential ).setApplicationName( "MOVIE-NIGHT" ).build();
        try {
            String calendarId = "primary";
            service.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
