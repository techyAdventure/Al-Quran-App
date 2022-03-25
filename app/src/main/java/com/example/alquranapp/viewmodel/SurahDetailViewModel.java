package com.example.alquranapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.alquranapp.repository.SurahDetailRepo;
import com.example.alquranapp.repository.SurahRepo;
import com.example.alquranapp.response.SurahDetailsResponse;
import com.example.alquranapp.response.SurahResponse;

public class SurahDetailViewModel extends ViewModel {

    public SurahDetailRepo surahDetailRepo;

    public SurahDetailViewModel() {
        surahDetailRepo = new SurahDetailRepo();
    }

    public LiveData<SurahDetailsResponse> getSurahDetail(String lan, int id){
        return surahDetailRepo.getSurahDetail(lan, id);
    }
}
