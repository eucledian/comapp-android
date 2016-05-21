package com.eucledian.comapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Token {

    @JsonIgnore
    private long id;
    private String token;
    private String secret;

    public Token() {}

    public Token(String token, String secret){
        setToken(token);
        setSecret(secret);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }
}

