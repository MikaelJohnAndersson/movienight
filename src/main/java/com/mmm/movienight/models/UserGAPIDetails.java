package com.mmm.movienight.models;

public class UserGAPIDetails {

    public String accesstoken;
    public String refreshtoken;
    public String expiresAt;

    public UserGAPIDetails(String accesstoken, String refreshtoken, String expiresInSeconds) {
        this.accesstoken = accesstoken;
        this.refreshtoken = refreshtoken;
        this.expiresAt = expiresAt;
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

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }
}
