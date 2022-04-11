package com.example.alquranapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alquranapp.R;
import com.example.alquranapp.adapter.MeaningAdapter;
import com.example.alquranapp.adapter.PhoeneticsAdapter;
import com.example.alquranapp.listener.NetworkChangedListener;
import com.example.alquranapp.listener.OnFetchDataListener;
import com.example.alquranapp.model.DictResponse;
import com.example.alquranapp.network.RequestManager;

public class DictionaryActivity extends AppCompatActivity {

    SearchView search;
    TextView word,pg_text;
    RecyclerView recyclerView_ph, recyclerView_means;
    ProgressDialog progressDialog;
    PhoeneticsAdapter phoeneticsAdapter;
    MeaningAdapter meaningAdapter;
    NetworkChangedListener networkChangedListener = new NetworkChangedListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        search = findViewById(R.id.search_dict);
        word = findViewById(R.id.word);
        recyclerView_ph= findViewById(R.id.recycler_ph);
        recyclerView_means = findViewById(R.id.recycler_means);
        progressDialog = new ProgressDialog(this);

        pg_text = findViewById(R.id.pg_text);

        //progressDialog.setTitle("Loading...");
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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

        //Toast.makeText(DictionaryActivity.this,"Show data found",Toast.LENGTH_SHORT).show();

        word.setText(dictResponse.getWord());

        recyclerView_ph.setHasFixedSize(true);
        recyclerView_ph.setLayoutManager(new GridLayoutManager(this,1));
        phoeneticsAdapter = new PhoeneticsAdapter(this, dictResponse.getPhonetics());
        recyclerView_ph.setAdapter(phoeneticsAdapter);

        recyclerView_means.setHasFixedSize(true);
        recyclerView_means.setLayoutManager(new GridLayoutManager(this,1));
        meaningAdapter = new MeaningAdapter(this, dictResponse.getMeanings());
        recyclerView_means.setAdapter(meaningAdapter);
    }
    @Override
    protected void onStart() {

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangedListener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangedListener);
        super.onStop();
    }

}
