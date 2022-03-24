package com.example.alquranapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.alquranapp.adapter.SurahAdapter;
import com.example.alquranapp.repository.SurahRepo;
import com.example.alquranapp.response.SurahResponse;

public class SurahViewModel extends ViewModel {

    private SurahRepo surahRepo;

    public  SurahViewModel(){
        surahRepo = new SurahRepo();
    }

    public LiveData<SurahResponse> getSurah(){
        return surahRepo.getSurah();
    }

//    public void loadIssues(String user, String repo){
//        SurahResponse response = SurahRepo.getIssues(user, repo).getValue();
//        mApiResponse.setValue(response);
//    }
}
