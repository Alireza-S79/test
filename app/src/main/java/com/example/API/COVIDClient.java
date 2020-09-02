package com.example.API;


import com.example.Models.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface COVIDClient {


    @GET("countries/irn")
    Call<Country> getEgyptCases();

    @GET("countries?sort=todayCases")
    Call<List<Country>> getAllCountries();

}
