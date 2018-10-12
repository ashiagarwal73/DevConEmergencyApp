package com.example.ashi.devconemergencyapp.rest;

/**
 * Created by Ashi on 20-03-2018.
 */

public class Location {
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

    double lat;
    double lng;
}
