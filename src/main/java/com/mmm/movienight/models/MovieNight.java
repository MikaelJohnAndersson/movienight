package com.mmm.movienight.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "movienights")
public class MovieNight {

    @Id
    private ObjectId id;

    private String admin;
    @JsonProperty("event")
    private FullCalendarEventDto event;
    @JsonProperty("members")
    private List<String> members;
    @JsonProperty("movies")
    private Movie movie;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public FullCalendarEventDto getEvent() {
        return event;
    }

    public void setEvent(FullCalendarEventDto suggestedTimes) {
        this.event = suggestedTimes;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
