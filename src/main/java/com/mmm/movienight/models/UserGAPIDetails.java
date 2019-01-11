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

    public Long getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public void setExpiresInSeconds( Long expiresInSeconds ) {
        this.expiresInSeconds = expiresInSeconds;
    }
}
