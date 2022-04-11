package com.example.alquranapp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class common {

    public static  boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null){
            NetworkInfo[] infos = connectivityManager.getAllNetworkInfo();
            if(infos!=null){

                for(int i = 0; i< infos.length;i++){
                    if(infos[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }

            }
        }
        return false;


    }
}
