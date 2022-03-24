package com.example.alquranapp.network;

import com.example.alquranapp.response.SurahResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("surah")
    Call<SurahResponse> getSurah();
}
