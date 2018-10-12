package com.example.ashi.devconemergencyapp.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ashi on 20-03-2018.
 */

public class Geometry {
    public Location1 getLocation() {
        return location;
    }

    public void setLocation(Location1 location) {
        this.location = location;
    }
    @SerializedName("location")
    Location1 location;
}
