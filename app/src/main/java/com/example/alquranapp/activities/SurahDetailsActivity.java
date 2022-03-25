package com.example.alquranapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.alquranapp.R;
import com.example.alquranapp.adapter.SurahAdapter;
import com.example.alquranapp.adapter.SurahDetailAdapter;
import com.example.alquranapp.common.common;
import com.example.alquranapp.model.Surah;
import com.example.alquranapp.model.SurahDetail;
import com.example.alquranapp.viewmodel.SurahDetailViewModel;
import com.example.alquranapp.viewmodel.SurahViewModel;

import java.util.ArrayList;
import java.util.List;

public class SurahDetailsActivity extends AppCompatActivity {

    private TextView surahName, surahType, surahTranslation;
    private int no;
    private RecyclerView recyclerView;
    private List<SurahDetail>list;
    private SurahDetailAdapter surahDetailAdapter;
    private SurahDetailViewModel surahDetailViewModel;
    private String eng = "english_rwwad";



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah_details);

        surahName = findViewById(R.id.surah_name);
        surahType = findViewById(R.id.type);
        surahTranslation = findViewById(R.id.translation);
        recyclerView = findViewById(R.id.surah_detail_recyclerView);
        no = getIntent().getIntExtra(common.SURAH_NO,0);
        surahName.setText(getIntent().getStringExtra(common.SURAH_NAME));

        surahType.setText(getIntent().getStringExtra(common.SURAH_TYPE)+ " "+
                getIntent().getIntExtra(common.SURAH_TOTAL_AYAH,0)+" Ayah");

        surahTranslation.setText(getIntent().getStringExtra(common.SURAH_TRANSLATION));

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();

        SurahTranslation(eng,no);

    }

    private void SurahTranslation(String lan, int id){

        if(list.size() >0){
            list.clear();
        }

        surahDetailViewModel = new ViewModelProvider(this).get(SurahDetailViewModel.class);
        surahDetailViewModel.getSurahDetail(lan,id).observe(this, surahDetailsResponse -> {

                for(int i = 0; i<surahDetailsResponse.getList().size();i++){
                    list.add(new SurahDetail(

                            surahDetailsResponse.getList().get(i).getId(),
                            surahDetailsResponse.getList().get(i).getSura(),
                            surahDetailsResponse.getList().get(i).getAya(),
                            surahDetailsResponse.getList().get(i).getArabic_text(),
                            surahDetailsResponse.getList().get(i).getTranslation(),
                            String.valueOf(surahDetailsResponse.getList().get(i).getFootnotes())
                    ));
                }



            if(list.size() != 0){
                surahDetailAdapter = new SurahDetailAdapter(this, list);
                recyclerView.setAdapter(surahDetailAdapter);

            }

        });


    }
}