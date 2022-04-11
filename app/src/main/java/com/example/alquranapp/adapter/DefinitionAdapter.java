package com.example.alquranapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codesgood.views.JustifiedTextView;
import com.example.alquranapp.R;
import com.example.alquranapp.listener.SurahListener;
import com.example.alquranapp.model.Definitions;
import com.example.alquranapp.model.Surah;

import java.util.ArrayList;
import java.util.List;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionAdapter.DefinitionViewHolder> {

    private Context context;
    private List<Definitions>Definitionlist;

    public DefinitionAdapter(Context context, List<Definitions> list) {
        this.context = context;
        this.Definitionlist = list;
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.definition_list,parent,false);

        return new DefinitionViewHolder(view);


        //return new DefinitionViewHolder(LayoutInflater.from(context).inflate(R.layout.definition_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {

        holder.text_def.setText(Definitionlist.get(position).getDefinition());
        holder.text_example.setText(Definitionlist.get(position).getExample());

//        StringBuilder syn = new StringBuilder();
//        StringBuilder ant = new StringBuilder();
//        syn.append(Definitionlist.get(position).getSynonym());
//        ant.append(Definitionlist.get(position).getAntonym());
//
//
//        if(syn.equals(null)){
//            holder.text_syn.setText("No synonym found");
//        }else{
//            holder.text_syn.setText(syn);
//        }
//
//        holder.text_ant.setText(ant);
//
//        holder.text_syn.setSelected(true);
//        holder.text_ant.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return Definitionlist.size();
    }


    public class DefinitionViewHolder extends RecyclerView.ViewHolder{

        private TextView text_def, text_example, text_syn, text_ant;


        public DefinitionViewHolder(@NonNull View itemView) {
            super(itemView);

            text_def = itemView.findViewById(R.id.text_def);
            text_example = itemView.findViewById(R.id.text_example);
//            text_syn = itemView.findViewById(R.id.text_synonym);
//            text_ant = itemView.findViewById(R.id.text_antonym);


        }
    }
}
