package com.example.moviecatalogue;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moviecatalogue.adapter.MovieAdapter;
import com.example.moviecatalogue.helper.CatalogueViewModel;
import com.example.moviecatalogue.helper.ModelMovie;

import java.util.ArrayList;

public class ReleaseTodayActivity extends AppCompatActivity {
    private ArrayList<ModelMovie> modelMovies = new ArrayList<>();
    private CatalogueViewModel catalogueViewModel;
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_today);

        progressBar = findViewById(R.id.progress_Bar);
        RecyclerView rvRelease = findViewById(R.id.rv_released);
        showLoading(true);

        movieAdapter = new MovieAdapter(this);
        movieAdapter.notifyDataSetChanged();

        catalogueViewModel = ViewModelProviders.of(this).get(CatalogueViewModel.class);
        loadDataReleased();

        rvRelease.setLayoutManager(new LinearLayoutManager(this));
        rvRelease.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickCallback(this::showSelectedReleased);
    }

    private Observer<ArrayList<ModelMovie>> getReleased = new Observer<ArrayList<ModelMovie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ModelMovie> modelM) {
            if (modelM != null){
                modelMovies.clear();
                modelMovies.addAll(modelM);
                movieAdapter.setData(modelMovies);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean yes) {
        if (yes) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showSelectedReleased(ModelMovie modelMovie) {
        Toast.makeText(this, modelMovie.getMovie_name(), Toast.LENGTH_SHORT).show();
    }

    private void loadDataReleased(){
        showLoading(true);
        catalogueViewModel.setReleased();
        catalogueViewModel.getReleased().observe(this, getReleased);
    }
}
