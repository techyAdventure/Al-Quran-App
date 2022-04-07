package com.example.alquranapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.alquranapp.R;
import com.example.alquranapp.adapter.SurahAdapter;
import com.example.alquranapp.adapter.SurahDetailAdapter;
import com.example.alquranapp.common.common;
import com.example.alquranapp.model.Surah;
import com.example.alquranapp.model.SurahDetail;
import com.example.alquranapp.viewmodel.SurahDetailViewModel;
import com.example.alquranapp.viewmodel.SurahViewModel;

import java.io.IOException;
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
    private EditText search;
    private ImageView settings;
    private String qariAB = "abdul_basit_murattal";
    private String qr ;
    private String str;
    Handler handler = new Handler();
    SeekBar seekBar;
    TextView startTime, totalTime;
    ImageButton play;
    MediaPlayer mediaPlayer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah_details);

        init();

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


        try {
            listenAudio(qariAB);
        } catch (IOException e) {
            e.printStackTrace();
        }

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

    }


    private void init(){
        surahName = findViewById(R.id.surah_name);
        surahType = findViewById(R.id.type);
        surahTranslation = findViewById(R.id.translation);
        recyclerView = findViewById(R.id.surah_detail_recyclerView);
        search = findViewById(R.id.search);
        settings = findViewById(R.id.settings);
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

    private void filter(String id) {

        ArrayList<SurahDetail>arrayList = new ArrayList<>();
        for(SurahDetail detail: list){
            if(String.valueOf(detail.getId()).contains(id)){
                arrayList.add(detail);
            }
        }
        surahDetailAdapter.filter(arrayList);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listenAudio(String qariAB) throws IOException {

        ColorStateList sl = ColorStateList.valueOf( Color.YELLOW);
        play = findViewById(R.id.play);
        startTime = findViewById(R.id.start_time);
        totalTime = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seekbar);
        seekBar.setSecondaryProgressTintList(sl);
        seekBar.setProgressBackgroundTintList(sl);

        mediaPlayer = new MediaPlayer();
        seekBar.setMax(100);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    play.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                }else{
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                    updateSeekbar();
                }
            }
        });
        
        preparedMediaPlayer(qariAB);

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                SeekBar seekBar = (SeekBar) view;
                int playPosition = (mediaPlayer.getDuration()/100)*seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                startTime.setText(timeToMilliSec(mediaPlayer.getCurrentPosition()));

                return false;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                seekBar.setSecondaryProgress(i);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                seekBar.setProgress(0);
                play.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                startTime.setText("0.00");
                totalTime.setText("0.00");
                mediaPlayer.reset();
                try {
                    preparedMediaPlayer(qariAB);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void preparedMediaPlayer(String qariAB) throws IOException {

        if(no<10){
           str = "00" + no;
        }else if(no<100){
            str = "0" + no;
        }else if(no>=100){
            str = String.valueOf(no);
        }
        //https://download.quranicaudio.com/quran/abdul_basit_murattal/001.mp3
        mediaPlayer.setDataSource("https://download.quranicaudio.com/quran/"+qariAB+"/"+str.trim()+".mp3");
        mediaPlayer.prepare();
        totalTime.setText(timeToMilliSec(mediaPlayer.getDuration()));
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekbar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            startTime.setText(timeToMilliSec(currentDuration));
        }
    };

    private void updateSeekbar(){

        if(mediaPlayer.isPlaying()){
            seekBar.setProgress((int) (((float)mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration())*100));
            handler.postDelayed(updater,1000);
        }
    }

    private String timeToMilliSec(long ms){

        String timeString = "";
        String secondString;

        int hours = (int) (ms/(1000*60*60));
        int min = (int)(ms%(100*60*60))/(1000*60);
        int sec = (int)((ms%(100*60*60)) % (1000*60)/1000);

        if(hours >0){
            timeString = hours + ":";
        }
        if(sec<10){
           secondString = "0" + sec;
        }else{
            secondString = "" + sec;
        }
        timeString = timeString + min + ":" +secondString;

        return timeString;

    }

    @Override
    protected void onDestroy() {
        if(mediaPlayer.isPlaying()){
            handler.removeCallbacks(updater);
            mediaPlayer.pause();
            play.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if(mediaPlayer.isPlaying()){
            handler.removeCallbacks(updater);
            mediaPlayer.pause();
            play.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
        super.onStop();
    }

    @Override
    protected void onPause() {
        if(mediaPlayer.isPlaying()){
            handler.removeCallbacks(updater);
            mediaPlayer.pause();
            play.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
        super.onPause();
    }
}