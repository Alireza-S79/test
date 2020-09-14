package com.example.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.API.RetrofitAPI;
import com.example.Models.AllCases;
import com.example.Models.Country;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorldViewModel extends ViewModel {

    MutableLiveData<ArrayList<Country>> countries;
    MutableLiveData<AllCases> allCases;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    MutableLiveData<Boolean> isFirstLoading = new MutableLiveData<>();
    MutableLiveData<Boolean> search = new MutableLiveData<>();

    MutableLiveData<Boolean> isError = new MutableLiveData<>();

    public void setIsFirstLoading(Boolean isFirstLoading) {
        this.isFirstLoading.postValue(isFirstLoading);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getIsError() {
        return isError;
    }

    public LiveData<Boolean> getIsFirstLoading() {
        return isFirstLoading;
    }


    public void getAllCases() {
        if (allCases == null) {
            isFirstLoading.postValue(true);
            allCases = new MutableLiveData<>();
            getWorldCases();
        }
    }

    private void getWorldCases() {
        isError.postValue(false);
        isLoading.postValue(true);
        Call<AllCases> call = RetrofitAPI.getInstance().getAllCases();
        call.enqueue(new Callback<AllCases>() {
            @Override
            public void onResponse(Call<AllCases> call, Response<AllCases> response) {
                isLoading.postValue(false);
                isFirstLoading.postValue(false);
                if (response.isSuccessful()) {
                    allCases.postValue(response.body());
                } else {
                    isError.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<AllCases> call, Throwable t) {
                isFirstLoading.postValue(false);
                isLoading.postValue(false);
                isError.postValue(true);
            }
        });
    }

    public LiveData<ArrayList<Country>> getAllCountries() {
        if (countries == null) {
            isFirstLoading.postValue(true);
            countries = new MutableLiveData<>();
            getCountriesCases();
        }
        return countries;
    }

    private void getCountriesCases() {
        isError.postValue(false);
        isLoading.postValue(true);
        Call<ArrayList<Country>> call = RetrofitAPI.getInstance().getAllCountries();
        call.enqueue(new Callback<ArrayList<Country>>() {
            @Override
            public void onResponse(Call<ArrayList<Country>> call, Response<ArrayList<Country>> response) {
                isLoading.postValue(false);
                isFirstLoading.postValue(false);
                if (response.isSuccessful()) {
                    countries.postValue(response.body());
                } else {
                    isError.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Country>> call, Throwable t) {
                isFirstLoading.postValue(false);
                isLoading.postValue(false);
                isError.postValue(true);
            }
        });
    }

    public void refresh() {
        getWorldCases();
        getCountriesCases();
    }
}