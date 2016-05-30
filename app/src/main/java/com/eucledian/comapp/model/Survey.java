package com.eucledian.comapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by joel on 29/05/16.
 */
public class Survey {

    @JsonIgnore
    private long id;

    public Survey(){ }

    public long getId() {
        return id;
    }

    //@JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }
}
