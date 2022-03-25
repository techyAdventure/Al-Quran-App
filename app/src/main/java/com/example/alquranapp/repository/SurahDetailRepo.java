package com.example.alquranapp.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.alquranapp.network.Api;
import com.example.alquranapp.network.JsonPlaceHolderApi;
import com.example.alquranapp.response.SurahDetailsResponse;
import com.example.alquranapp.response.SurahResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurahDetailRepo {

    JsonPlaceHolderApi jsonPlaceHolderApi;

    public SurahDetailRepo() {

        jsonPlaceHolderApi = Api.getInstance().create(JsonPlaceHolderApi.class);

    }

    public LiveData<SurahDetailsResponse> getSurahDetail(String lan, int id){

        MutableLiveData<SurahDetailsResponse> data = new MutableLiveData<>();
        jsonPlaceHolderApi.getSurahDetail(lan, id).enqueue(new Callback<SurahDetailsResponse>() {
            @Override
            public void onResponse(Call<SurahDetailsResponse> call, Response<SurahDetailsResponse> response) {

                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SurahDetailsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
