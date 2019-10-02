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
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.helper.ModelTVShow;
import com.example.moviecatalogue.adapter.TVShowAdapter;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemTVShowFragment extends Fragment {
    private ArrayList<ModelTVShow> modelTvShows = new ArrayList<>();
    private ProgressBar progressBar;
    private TVShowAdapter tvShowAdapter;
    private CatalogueViewModel catalogueViewModel;

    public ItemTVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_item_tvshow, container, false);

        progressBar = v.findViewById(R.id.progressBar);
        RecyclerView rvTVShow = v.findViewById(R.id.rv_tvShow);
        showLoading(true);
        setHasOptionsMenu(true);

        tvShowAdapter = new TVShowAdapter(getActivity());
        tvShowAdapter.notifyDataSetChanged();

        catalogueViewModel = ViewModelProviders.of(this).get(CatalogueViewModel.class);
        loadDataTVShow();

        rvTVShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTVShow.setAdapter(tvShowAdapter);

        tvShowAdapter.setOnItemClickCallback(this::showSelectedTVShow);
        return v;
    }

    private Observer<ArrayList<ModelTVShow>> getTVShow = new Observer<ArrayList<ModelTVShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ModelTVShow> modelTv) {
            if(modelTv !=null){
                modelTvShows.clear();
                modelTvShows.addAll(modelTv);
                tvShowAdapter.setData(modelTvShows);
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

    private void showSelectedTVShow(ModelTVShow modelTvShow) {
        Toast.makeText(getContext(), modelTvShow.getTvshow_name(), Toast.LENGTH_SHORT).show();
    }

    private void loadDataTVShow(){
        showLoading(true);
        catalogueViewModel.setTVShow();
        catalogueViewModel.getTVShow().observe(this, getTVShow);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu_nav, menu);
        TVShowSearching(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void TVShowSearching(Menu menu) {
        SearchManager tvShowSearchManager;
        if (getContext() != null){
            tvShowSearchManager=(SearchManager)getContext().getSystemService(Context.SEARCH_SERVICE);
            if (tvShowSearchManager!=null){
                SearchView tvShowSearchView = (SearchView) (menu.findItem(R.id.nav_search).getActionView());
                tvShowSearchView.setSearchableInfo(tvShowSearchManager.getSearchableInfo(Objects.requireNonNull(getActivity()).getComponentName()));
                tvShowSearchView.setIconifiedByDefault(false);
                tvShowSearchView.setFocusable(true);
                tvShowSearchView.setIconified(false);
                tvShowSearchView.requestFocusFromTouch();
                tvShowSearchView.setMaxWidth(Integer.MAX_VALUE);
                tvShowSearchView.setQueryHint(getString(R.string.search_tvshow));
                SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        tvShowSearchView.setQuery(query,false);
                        tvShowSearchView.setIconified(false);
                        tvShowSearchView.clearFocus();
                        String searchUrl = "https://api.themoviedb.org/3/search/tv?api_key=" + BuildConfig.MY_API_KEY +"&language=en-US&query=" + query;
                        SearchQueryTVShow(searchUrl);
                        keyboardTVShow(tvShowSearchView);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (!newText.equals("")){
                            String searchUrl = "https://api.themoviedb.org/3/search/tv?api_key=" + BuildConfig.MY_API_KEY +"&language=en-US&query=" + newText;
                            SearchQueryTVShow(searchUrl);
                        }
                        return true;
                    }
                };
                tvShowSearchView.setOnQueryTextListener(queryTextListener);

                MenuItem searchItem = menu.findItem(R.id.nav_search);
                searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        loadDataTVShow();
                        return true;
                    }
                });
            }
        }
    }

    private void SearchQueryTVShow(String url) {
        modelTvShows.clear();
        tvShowAdapter.setData(modelTvShows);
        showLoading(true);
        catalogueViewModel.setResultTVShow(url);
        catalogueViewModel.getResultTVShow().observe(this, getResultTVShow);
    }

    private void keyboardTVShow(SearchView searchView) {
        if(getContext()!=null){
            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(searchView.getWindowToken(),0);
        }
    }

    private Observer<ArrayList<ModelTVShow>> getResultTVShow = new Observer<ArrayList<ModelTVShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ModelTVShow> modelTvShows) {
            assert modelTvShows != null;
            if (modelTvShows.size() > 0) {
                tvShowAdapter.setData(modelTvShows);
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
