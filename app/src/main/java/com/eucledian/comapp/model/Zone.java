package com.eucledian.comapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by joel on 30/05/16.
 */
public class Zone {

    private long id;
    private String name;
    private double lat;
    private double lng;

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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return name;
    }
}
