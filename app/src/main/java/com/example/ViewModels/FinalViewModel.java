package com.example.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.API.RetrofitAPI;
import com.example.Models.FinalCountry;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalViewModel extends ViewModel {

    MutableLiveData<FinalCountry> finalcountry;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    MutableLiveData<Boolean> isFirstLoading = new MutableLiveData<>();
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

    public LiveData<FinalCountry> getFinalCountry() {
        if (finalcountry == null) {
            isFirstLoading.postValue(true);
            finalcountry = new MutableLiveData<>();
            getWorldCasesTWO();
        }
        return finalcountry;
    }

    private void getWorldCasesTWO() {

        isError.postValue(false);
        isLoading.postValue(true);

        Call<FinalCountry> call = RetrofitAPI.getInstance().getFinalCountry();
        call.enqueue(new Callback<FinalCountry>() {
            @Override
            public void onResponse(Call<FinalCountry> call, Response<FinalCountry> response) {
                isLoading.postValue(false);
                isFirstLoading.postValue(false);
                if (response.isSuccessful()) {
                    finalcountry.postValue(response.body());
                } else {
                    isError.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<FinalCountry> call, Throwable t) {

                isFirstLoading.postValue(false);
                isLoading.postValue(false);
                isError.postValue(true);

            }
        });
    }

    public void refresh() {
        getWorldCasesTWO();
    }

    public void reSet() {
        isError.setValue(false);
        isLoading.setValue(false);
    }

}
