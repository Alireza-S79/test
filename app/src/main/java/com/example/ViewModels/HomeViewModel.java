package com.example.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.API.RetrofitAPI;
import com.example.Models.AllCases;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    MutableLiveData<AllCases> allCases;
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

    public LiveData<AllCases> getAllCases() {
        if (allCases == null) {
            isFirstLoading.postValue(true);
            allCases = new MutableLiveData<>();
            getWorldCases();
        }
        return allCases;
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

    public void refresh() {
        getWorldCases();
    }

    public void reSet() {
        isError.setValue(false);
        isLoading.setValue(false);
    }

}