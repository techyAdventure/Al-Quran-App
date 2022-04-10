package com.example.alquranapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alquranapp.R;
import com.example.alquranapp.listener.SurahListener;
import com.example.alquranapp.model.Phoenetics;
import com.example.alquranapp.model.Surah;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class PhoeneticsAdapter extends RecyclerView.Adapter<PhoeneticsAdapter.PhViewHolder> {

    private Context context;
    private List<Phoenetics>list;

    public PhoeneticsAdapter(Context context, List<Phoenetics> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.phoenetic_list,parent,false);

        return new PhViewHolder(view);
        //return new PhViewHolder(LayoutInflater.from(context).inflate(R.layout.phoenetic_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.text_ph.setText(list.get(position).getText());
//        holder.audio_dict.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MediaPlayer player = new MediaPlayer();
//
//                try {
//                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                    player.reset();
//                    player.setDataSource("https:"+ list.get(position).getAudio());
//                    player.prepare();
//                    player.start();
//                }catch (Exception e){
//                    e.printStackTrace();
//                    Toast.makeText(context,"Can't play audio"+e,Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class PhViewHolder extends RecyclerView.ViewHolder{

        public TextView text_ph;
        //public ImageButton audio_dict;


        public PhViewHolder(@NonNull View itemView) {
            super(itemView);

            text_ph = itemView.findViewById(R.id.text_ph);
            //audio_dict = itemView.findViewById(R.id.audio_word);

        }


    }
}
