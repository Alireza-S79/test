package com.example.API;

import com.example.Models.AllCases;
import com.example.Models.Country;
import com.example.Models.FinalCountry;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface COVIDClient {

    @GET("all")
    Call<AllCases> getAllCases();

    @GET("all")
    Call<FinalCountry> getFinalCountry();

    @GET("countries/irn")
    Call<Country> getIranCases();

    @GET("countries?sort=todayCases")
    Call<ArrayList<Country>> getAllCountries();

}