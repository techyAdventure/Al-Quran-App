package com.example.alquranapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.alquranapp.activities.SurahDetailsActivity;
import com.example.alquranapp.adapter.SurahAdapter;
import com.example.alquranapp.common.common;
import com.example.alquranapp.listener.SurahListener;
import com.example.alquranapp.model.Surah;
import com.example.alquranapp.response.SurahResponse;
import com.example.alquranapp.viewmodel.SurahViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SurahListener {

    private RecyclerView recyclerView;
    private SurahAdapter surahAdapter;
    private List<Surah> list;
    private SurahViewModel surahViewModel;
    private SurahResponse surahResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        recyclerView = findViewById(R.id.surah);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        surahViewModel = new ViewModelProvider(this).get(SurahViewModel.class);
        surahViewModel.getSurah().observe(this, surahResponse -> {
            if(surahResponse != null){
                for(int i = 0; i<surahResponse.getList().size();i++){
                    list.add(new Surah(
                            surahResponse.getList().get(i).getNumber(),
                            String.valueOf(surahResponse.getList().get(i).getName()),
                            String.valueOf(surahResponse.getList().get(i).getEnglishName()),
                            String.valueOf(surahResponse.getList().get(i).getEnglishNameTranslation()),
                            surahResponse.getList().get(i).getNumberOfAyahs(),
                            String.valueOf(surahResponse.getList().get(i).getRevelationType())
                    ));
                }
            }


            if(list.size() != 0){
                surahAdapter = new SurahAdapter(this, list,this::onSurahListener);
                recyclerView.setAdapter(surahAdapter);
                surahAdapter.notifyDataSetChanged();
            }

        });
    }

    @Override
    public void onSurahListener(int position) {
        Intent intent = new Intent(MainActivity.this, SurahDetailsActivity.class);
        intent.putExtra(common.SURAH_NO,list.get(position).getNumber());
        intent.putExtra(common.SURAH_NAME,list.get(position).getName());
        intent.putExtra(common.SURAH_TOTAL_AYAH,list.get(position).getNumberOfAyahs());
        intent.putExtra(common.SURAH_TYPE,list.get(position).getRevelationType());
        intent.putExtra(common.SURAH_TRANSLATION,list.get(position).getEnglishNameTranslation());
        startActivity(intent);

    }
}