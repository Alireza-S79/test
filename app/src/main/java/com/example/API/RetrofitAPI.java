package com.example.API;

import com.example.Models.AllCases;
import com.example.Models.Country;
import com.example.Models.FinalCountry;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPI {

    private static RetrofitAPI ourInstance;
    private static Retrofit base;
    private static COVIDClient covidClient;

    public static RetrofitAPI getInstance() {
        if(ourInstance == null){
            ourInstance = new RetrofitAPI();
        }
        return ourInstance;
    }

    RetrofitAPI() {
        base = new Retrofit.Builder()
                .baseUrl("https://corona.lmao.ninja/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        covidClient = base.create(COVIDClient.class);
    }

    public Call<Country> getIranCases(){
        return covidClient.getIranCases();
    }

    public Call<ArrayList<Country>> getAllCountries(){
        return covidClient.getAllCountries();
    }

    public Call<AllCases> getAllCases(){
        return covidClient.getAllCases();
    }
    public Call<FinalCountry> getFinalCountry() {
        return covidClient.getFinalCountry();
    }

}