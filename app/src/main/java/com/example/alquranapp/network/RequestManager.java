package com.example.alquranapp.network;

import android.content.Context;
import android.widget.Toast;

import com.example.alquranapp.listener.OnFetchDataListener;
import com.example.alquranapp.model.DictResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RequestManager {

    Context context;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getWordMeanings(OnFetchDataListener listener, String word){
        CallDictionary callDictionary = retrofit.create(CallDictionary.class);
        Call<List<DictResponse>> call = callDictionary.callMeanings(word);

        try {
            call.enqueue(new Callback<List<DictResponse>>() {
                @Override
                public void onResponse(Call<List<DictResponse>> call, Response<List<DictResponse>> response) {

                    if(!response.isSuccessful()){
                        Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    listener.onFetchData(response.body().get(0),response.message());

                }

                @Override
                public void onFailure(Call<List<DictResponse>> call, Throwable t) {

                    listener.onError("Request Failed");

                }
            });
        }catch (Exception e){
           e.printStackTrace();
           Toast.makeText(context,"Error "+e,Toast.LENGTH_SHORT).show();
        }
    }

    public interface CallDictionary{
        @GET("entries/en/{word}")
        Call<List<DictResponse>>callMeanings(
                @Path("word") String word
        );
    }
}

