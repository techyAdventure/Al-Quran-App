package com.example.alquranapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alquranapp.R;
import com.example.alquranapp.adapter.MeaningAdapter;
import com.example.alquranapp.adapter.PhoeneticsAdapter;
import com.example.alquranapp.listener.OnFetchDataListener;
import com.example.alquranapp.model.DictResponse;
import com.example.alquranapp.network.RequestManager;

public class DictionaryActivity extends AppCompatActivity {

    SearchView search;
    TextView word;
    RecyclerView recyclerView_ph, recyclerView_means;
    ProgressDialog progressDialog;
    PhoeneticsAdapter phoeneticsAdapter;
    MeaningAdapter meaningAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        search = findViewById(R.id.search_dict);
        word = findViewById(R.id.word);
        recyclerView_ph= findViewById(R.id.recycler_ph);
        recyclerView_means = findViewById(R.id.recycler_means);
        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Loading...");
        progressDialog.show();
        RequestManager manager = new RequestManager(DictionaryActivity.this);
        manager.getWordMeanings(listener,"Knowledge");

        recyclerView_ph.setHasFixedSize(true);
        recyclerView_ph.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView_means.setHasFixedSize(true);
        recyclerView_means.setLayoutManager(new GridLayoutManager(this,1));

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Searching...");
                progressDialog.show();
                RequestManager manager = new RequestManager(DictionaryActivity.this);
                manager.getWordMeanings(listener,query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    private final OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onFetchData(DictResponse dictResponse, String message) {
            progressDialog.dismiss();
            if(dictResponse==null){
                Toast.makeText(DictionaryActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                return;
            }
            showData(dictResponse);
        }



        @Override
        public void onError(String message) {
            progressDialog.dismiss();
            Toast.makeText(DictionaryActivity.this,message,Toast.LENGTH_SHORT).show();

        }
    };

    private void showData(DictResponse dictResponse) {

        Toast.makeText(DictionaryActivity.this,"Show data found",Toast.LENGTH_SHORT).show();

        word.setText("Word: "+ dictResponse.getWord());

        recyclerView_ph.setHasFixedSize(true);
        recyclerView_ph.setLayoutManager(new GridLayoutManager(this,1));
        phoeneticsAdapter = new PhoeneticsAdapter(this, dictResponse.getPhonetics());
        recyclerView_ph.setAdapter(phoeneticsAdapter);

        recyclerView_means.setHasFixedSize(true);
        recyclerView_means.setLayoutManager(new GridLayoutManager(this,1));
        meaningAdapter = new MeaningAdapter(this, dictResponse.getMeanings());
        recyclerView_means.setAdapter(meaningAdapter);
    }
}
