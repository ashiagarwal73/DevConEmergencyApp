package com.example.ashi.devconemergencyapp.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ashi on 20-03-2018.
 */

public class Results {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    String vicinity;

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    String place_id;
    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
    @SerializedName("geometry")
    Geometry geometry;




    public static final String HEADER_TYPE="HEADER_TYPE";
    public static final String SUB_HEADER_TYPE="SUB_HEADER_TYPE";
    String viewType="";
    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    String headerText;

}
