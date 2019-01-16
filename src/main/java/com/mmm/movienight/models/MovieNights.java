package com.mmm.movienight.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class MovieNights {

    //Inner class needs to be static for Spring to serialize
    private static class EventTime {

        @JsonProperty("starttime")
        private String startTime;
        @JsonProperty("endtime")
        private String endTime;

    }

    @Id
    private ObjectId id;

    private String admin;
    @JsonProperty("times")
    private EventTime[] suggestedTimes;
    @JsonProperty("members")
    private ArrayList<User> members;
    @JsonProperty("movies")
    private Movie[] suggestedMovies;
    @JsonProperty("end")
    private String endTime;

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

    public EventTime[] getSuggestedTimes() {
        return suggestedTimes;
    }

    public void setSuggestedTimes(EventTime[] suggestedTimes) {
        this.suggestedTimes = suggestedTimes;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList members) {
        this.members = members;
    }

    public Movie[] getSuggestedMovies() {
        return suggestedMovies;
    }

    public void setSuggestedMovies(Movie[] suggestedMovies) {
        this.suggestedMovies = suggestedMovies;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
