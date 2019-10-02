package com.example.moviecatalogue;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.moviecatalogue.fragment.MovieFragment;
import com.example.moviecatalogue.fragment.TVShowFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = menuItem -> {
        Fragment mainfragment;

        switch (menuItem.getItemId()){
            case R.id.nav_movie:
                mainfragment = new MovieFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_frame, mainfragment, mainfragment.getClass().getSimpleName())
                        .commit();
                return true;
            case R.id.nav_tvshow:
                mainfragment = new TVShowFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_frame, mainfragment, mainfragment.getClass().getSimpleName())
                        .commit();
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if (savedInstanceState == null){
            bottomNavigationView.setSelectedItemId(R.id.nav_movie);
        }
    }
}
