package com.example.moviecatalogue.fragment;


import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.moviecatalogue.BuildConfig;
import com.example.moviecatalogue.AppSettingActivity;
import com.example.moviecatalogue.helper.CatalogueViewModel;
import com.example.moviecatalogue.helper.ModelMovie;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.MovieAdapter;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemMovieFragment extends Fragment {
    private ArrayList<ModelMovie> modelMovies = new ArrayList<>();
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;
    private CatalogueViewModel catalogueViewModel;

    public ItemMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_item_movie, container, false);

        progressBar = v.findViewById(R.id.progressBar);
        RecyclerView rvMovie = v.findViewById(R.id.rv_movie);
        showLoading(true);
        setHasOptionsMenu(true);

        movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.notifyDataSetChanged();

        catalogueViewModel = ViewModelProviders.of(this).get(CatalogueViewModel.class);
        loadDataMovie();

        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickCallback(this::showSelectedMovie);
        return v;
    }

    private  Observer<ArrayList<ModelMovie>> getMovie = new Observer<ArrayList<ModelMovie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ModelMovie> modelM) {
            if(modelM !=null){
                modelMovies.clear();
                modelMovies.addAll(modelM);
                movieAdapter.setData(modelMovies);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showSelectedMovie(ModelMovie modelMovie) {
        Toast.makeText(getContext(), modelMovie.getMovie_name(), Toast.LENGTH_SHORT).show();
    }

    private void loadDataMovie(){
        showLoading(true);
        catalogueViewModel.setMovie();
        catalogueViewModel.getMovie().observe(this, getMovie);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu_nav, menu);
        MovieSearching(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void MovieSearching(Menu menu) {
        SearchManager movieSearchManager;
        if (getContext() != null){
            movieSearchManager=(SearchManager)getContext().getSystemService(Context.SEARCH_SERVICE);
            if (movieSearchManager!=null){
                SearchView movieSearchView = (SearchView) (menu.findItem(R.id.nav_search).getActionView());
                movieSearchView.setSearchableInfo(movieSearchManager.getSearchableInfo(Objects.requireNonNull(getActivity()).getComponentName()));
                movieSearchView.setIconifiedByDefault(false);
                movieSearchView.setFocusable(true);
                movieSearchView.setIconified(false);
                movieSearchView.requestFocusFromTouch();
                movieSearchView.setMaxWidth(Integer.MAX_VALUE);
                movieSearchView.setQueryHint(getString(R.string.search_movie));
                SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        movieSearchView.setQuery(query,false);
                        movieSearchView.setIconified(false);
                        movieSearchView.clearFocus();
                        String searchUrl = "https://api.themoviedb.org/3/search/movie?api_key=" + BuildConfig.MY_API_KEY +"&language=en-US&query=" + query;
                        SearchQueryMovie(searchUrl);
                        keyboardMovie(movieSearchView);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (!newText.equals("")){
                            String searchUrl = "https://api.themoviedb.org/3/search/movie?api_key=" + BuildConfig.MY_API_KEY +"&language=en-US&query=" + newText;
                            SearchQueryMovie(searchUrl);
                        }
                        return true;
                    }
                };
                movieSearchView.setOnQueryTextListener(queryTextListener);

                MenuItem searchItem = menu.findItem(R.id.nav_search);
                searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        loadDataMovie();
                        return true;
                    }
                });
            }
        }
    }

    private void SearchQueryMovie(String url) {
        modelMovies.clear();
        movieAdapter.setData(modelMovies);
        showLoading(true);
        catalogueViewModel.setResultMovies(url);
        catalogueViewModel.getResultMovies().observe(this, getResultMovies);
    }

    private void keyboardMovie(SearchView searchView) {
        if(getContext()!=null){
            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(searchView.getWindowToken(),0);
        }
    }

    private Observer<ArrayList<ModelMovie>> getResultMovies = new Observer<ArrayList<ModelMovie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ModelMovie> modelMovies) {
            assert modelMovies != null;
            if (modelMovies.size() > 0) {
                movieAdapter.setData(modelMovies);
                showLoading(false);
            } else {
                showLoading(false);
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_setting) {
            Intent settingIntent = new Intent(getActivity(), AppSettingActivity.class);
            startActivity(settingIntent);
            return true;
        }
        if (item.getItemId() == R.id.nav_language) {
            Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(languageIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
