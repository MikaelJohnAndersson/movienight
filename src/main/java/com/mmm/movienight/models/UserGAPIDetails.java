package com.mmm.movienight.models;

public class UserGAPIDetails {

    public String accesstoken;
    public String refreshtoken;
    public Long expiresInSeconds;

    public UserGAPIDetails(String accesstoken, String refreshtoken, Long expiresInSeconds) {
        this.accesstoken = accesstoken;
        this.refreshtoken = refreshtoken;
        this.expiresInSeconds = expiresInSeconds;
    }
}
