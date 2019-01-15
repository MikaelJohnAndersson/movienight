package com.mmm.movienight.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    public ObjectId id;

    public String username;
    public String password;
    public UserGAPIDetails gapiDetails;

    public User(ObjectId id, String username, String password, UserGAPIDetails gapiDetails) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gapiDetails = gapiDetails;
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
