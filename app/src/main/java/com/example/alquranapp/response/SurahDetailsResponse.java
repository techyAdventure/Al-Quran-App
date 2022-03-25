package com.example.alquranapp.response;

import com.example.alquranapp.model.Surah;
import com.example.alquranapp.model.SurahDetail;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SurahDetailsResponse {

    @SerializedName("result")
    private List<SurahDetail> list;

    public List<SurahDetail> getList() {
        return list;
    }

    public void setList(List<SurahDetail> list) {
        this.list = list;
    }
}

