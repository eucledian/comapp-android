package com.eucledian.comapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppUser {

    @JsonIgnore
    private long id;
    private String name;
    private String lastNames;
    private String mail;
    private int totalPoints;

    public AppUser(){ }

    public AppUser(String name, String lastNames, String mail){
        this.name = name;
        this.lastNames = lastNames;
        this.mail = mail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastNames() {
        return lastNames;
    }

    @JsonProperty("last_names")
    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFullName(){
        return name + " " + lastNames;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    @JsonProperty("total_points")
    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastNames='" + lastNames + '\'' +
                ", mail='" + mail + '\'' +
                ", totalPoints=" + totalPoints +
                '}';
    }
}
