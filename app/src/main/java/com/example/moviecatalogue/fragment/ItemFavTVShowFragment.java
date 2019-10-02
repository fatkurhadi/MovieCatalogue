package com.example.moviecatalogue.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviecatalogue.AppSettingActivity;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.TVShowAdapter;
import com.example.moviecatalogue.helper.CatalogueCallback;
import com.example.moviecatalogue.helper.ModelTVShow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.moviecatalogue.database.DBContract.tvSHOW_URI;
import static com.example.moviecatalogue.helper.MappingCatalogue.mapCursorToArrayListTVShow;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFavTVShowFragment extends Fragment implements CatalogueCallback {
    private RecyclerView rvFavTVShow;
    private ProgressBar progressBar;
    private TVShowAdapter tvShowAdapter;
    private TextView textView;
    private static final String EXTRA_STATE_TVShow = "EXTRA_STATE_TVShow";


    public ItemFavTVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_item_fav_tvshow, container, false);
        textView = v.findViewById(R.id.no_tvShow);
        progressBar = v.findViewById(R.id.progress_Bar);
        rvFavTVShow = v.findViewById(R.id.rv_fav_tvShow);
        setHasOptionsMenu(true);
        rvFavTVShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        ItemFavTVShowFragment.DataObserver observer = new ItemFavTVShowFragment.DataObserver(handler, getContext());
        if (getActivity() != null){
            getActivity().getContentResolver().registerContentObserver(tvSHOW_URI, true, observer);
        }
        tvShowAdapter = new TVShowAdapter(getActivity());
        rvFavTVShow.setAdapter(tvShowAdapter);

        tvShowAdapter.setOnItemClickCallback(this::showSelectedTVShow);

        if (savedInstanceState == null){
            new ItemFavTVShowFragment.LoadTVShowAsync(getContext(), this).execute();
        } else {
            ArrayList<ModelTVShow> tv = savedInstanceState.getParcelableArrayList(EXTRA_STATE_TVShow);
            if (tv != null){
                tvShowAdapter.setData(tv);
            }
        }
        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE_TVShow, tvShowAdapter.getListData());
    }

    @Override
    public void onResume() {
        super.onResume();
        new ItemFavTVShowFragment.LoadTVShowAsync(getContext(), this).execute();
    }

    @Override
    public void preExecute() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(()
                    -> progressBar.setVisibility(View.VISIBLE));
        }
    }

    @Override
    public void postExecute(Cursor cursor) {
        progressBar.setVisibility(View.GONE);
        ArrayList<ModelTVShow> modelTvShows = mapCursorToArrayListTVShow(cursor);
        if (modelTvShows.size() > 0){
            noCollection(false);
            tvShowAdapter.setData(modelTvShows);
        } else {
            rvFavTVShow.setVisibility(View.INVISIBLE);
            noCollection(true);
        }
    }

    private void noCollection(boolean yes) {
        if (yes) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    private void showSelectedTVShow(ModelTVShow modelTvShow) {
        Toast.makeText(getContext(), modelTvShow.getTvshow_name(), Toast.LENGTH_SHORT).show();
    }

    private static class LoadTVShowAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<CatalogueCallback> weakCallback;

        private LoadTVShowAsync(Context context, CatalogueCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(tvSHOW_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.setting_menu_nav, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

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
