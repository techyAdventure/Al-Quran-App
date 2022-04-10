package com.example.alquranapp.listener;

import com.example.alquranapp.model.DictResponse;

public interface OnFetchDataListener {

    void onFetchData(DictResponse dictResponse, String message);
    void onError(String message);
}
