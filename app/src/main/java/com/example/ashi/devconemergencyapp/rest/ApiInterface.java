package com.example.ashi.devconemergencyapp.rest;

import com.example.ashi.devconemergencyapp.Model.Places;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ashi on 05-02-2018.
 */

public interface ApiInterface {
    @GET("nearbysearch/json")
    Call<Places> getPlacesReport(@Query("location") String location, @Query("radius") String radius, @Query("type") String type, @Query("key") String key);
    }