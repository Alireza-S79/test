package com.example.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.API.RetrofitAPI;
import com.example.Models.Country;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IRViewModel extends ViewModel {
    MutableLiveData<Country> iran;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    MutableLiveData<Boolean> isError = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getIsError() {
        return isError;
    }

    public LiveData<Country> getIran() {
        if (iran == null) {
            iran = new MutableLiveData<>();
            getNewCases();
        }
        return iran;
    }

    private void getNewCases() {
        isError.postValue(false);
        isLoading.postValue(true);
        Call<Country> call = RetrofitAPI.getInstance().getIranCases();
        call.enqueue(new Callback<Country>() {
            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                isLoading.postValue(false);
                if (response.isSuccessful()) {
                    iran.postValue(response.body());
                } else {
                    isError.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<Country> call, Throwable t) {
                isError.postValue(true);
                isLoading.postValue(false);
            }
        });
    }

    public void refresh() {
        getNewCases();
    }

    public void reSet() {
        isError.setValue(false);
        isLoading.setValue(false);
    }
}