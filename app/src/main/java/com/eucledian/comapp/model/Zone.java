package com.eucledian.comapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by joel on 30/05/16.
 */
public class Zone {

    @JsonIgnore
    private long id;

    private String name;

    public Zone(){ }

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
}
