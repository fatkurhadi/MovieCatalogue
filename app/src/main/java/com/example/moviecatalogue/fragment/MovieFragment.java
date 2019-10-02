package com.example.moviecatalogue.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.VPAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout favTabLayout = view.findViewById(R.id.tab_nav);
        ViewPager favViewPager = view.findViewById(R.id.movie_pager);
        VPAdapter adapter = new VPAdapter(getChildFragmentManager());
        adapter.addFragment(new ItemMovieFragment(), getString(R.string.nav_movie));
        adapter.addFragment(new ItemFavMovieFragment(), getString(R.string.nav_fav));
        favViewPager.setAdapter(adapter);
        favTabLayout.setupWithViewPager(favViewPager);
    }
}
