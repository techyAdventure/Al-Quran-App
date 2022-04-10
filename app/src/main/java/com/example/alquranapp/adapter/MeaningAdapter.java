package com.example.alquranapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alquranapp.R;
import com.example.alquranapp.listener.SurahListener;
import com.example.alquranapp.model.Meanings;
import com.example.alquranapp.model.Surah;

import java.util.ArrayList;
import java.util.List;

public class MeaningAdapter extends RecyclerView.Adapter<MeaningAdapter.Meaning_ViewHolder> {

    private Context context;
    private List<Meanings>list;

    public MeaningAdapter(Context context, List<Meanings> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public Meaning_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.meaning_list,parent,false);

        return new Meaning_ViewHolder(view);

        //return new Meaning_ViewHolder(LayoutInflater.from(context).inflate(R.layout.meaning_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Meaning_ViewHolder holder, int position) {

        holder.text_ps.setText("Parts of Speech: "+list.get(position).getPartOfSpeech());
        holder.recyclerView_def.setHasFixedSize(true);
        holder.recyclerView_def.setLayoutManager(new GridLayoutManager(context,1));
        DefinitionAdapter definitionAdapter = new DefinitionAdapter(context,list.get(position).getDefinitions());
        holder.recyclerView_def.setAdapter(definitionAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Meaning_ViewHolder extends RecyclerView.ViewHolder{

        TextView text_ps;
        RecyclerView recyclerView_def;


        public Meaning_ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_ps = itemView.findViewById(R.id.text_ps);
            recyclerView_def = itemView.findViewById(R.id.recycler_def);

        }
    }
}
