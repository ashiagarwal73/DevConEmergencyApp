package com.example.ashi.devconemergencyapp.rest;


import java.util.List;

/**
 * Created by Ashi on 20-03-2018.
 */

public class Places {


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
    List<Results> results;
    String status;
}
