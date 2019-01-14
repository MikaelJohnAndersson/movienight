package com.mmm.movienight.models;

public class UserGAPIDetails {

    public String accesstoken;
    public String refreshtoken;
    public String expiresInSeconds;

    public UserGAPIDetails(String accesstoken, String refreshtoken, String expiresInSeconds) {
        this.accesstoken = accesstoken;
        this.refreshtoken = refreshtoken;
        this.expiresInSeconds = expiresInSeconds;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken( String accesstoken ) {
        this.accesstoken = accesstoken;
    }

    public String getRefreshtoken() {
        return refreshtoken;
    }

    public void setRefreshtoken( String refreshtoken ) {
        this.refreshtoken = refreshtoken;
    }

    public String getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public void setExpiresInSeconds(String expiresInSeconds ) {
        this.expiresInSeconds = expiresInSeconds;
    }
}
