package com.mmm.movienight.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.mmm.movienight.config.Views;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users")
public class User {

    @Id
    private ObjectId id;

    @JsonView(Views.Public.class)
    private String username;
    private String password;
    private UserGAPIDetails gapiDetails;

    public User(ObjectId id, String username, String password, UserGAPIDetails gapiDetails) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gapiDetails = gapiDetails;
    }

    public boolean tokenIsExpired(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime accessTokenExpire = LocalDateTime.parse(this.gapiDetails.expiresAt);
        return accessTokenExpire.isBefore(now);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserGAPIDetails getGapiDetails() {
        return gapiDetails;
    }

    public void setGapiDetails(UserGAPIDetails gapiDetails) {
        this.gapiDetails = gapiDetails;
    }
}
