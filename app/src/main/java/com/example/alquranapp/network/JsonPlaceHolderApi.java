package com.example.alquranapp.network;

import com.example.alquranapp.response.SurahDetailsResponse;
import com.example.alquranapp.response.SurahResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {
    @GET("surah")
    Call<SurahResponse> getSurah();

    @GET("sura/{language}/{id}")
    Call<SurahDetailsResponse> getSurahDetail(@Path("language")String lan, @Path("id")int surahId);

}
