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
import com.example.moviecatalogue.helper.CatalogueCallback;
import com.example.moviecatalogue.helper.ModelMovie;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.MovieAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.moviecatalogue.database.DBContract.MOVIE_URI;
import static com.example.moviecatalogue.helper.MappingCatalogue.mapCursorToArrayListMovie;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFavMovieFragment extends Fragment implements CatalogueCallback {
    private RecyclerView rvFavMovie;
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;
    private TextView textView;
    private static final String EXTRA_STATE_Movie = "EXTRA_STATE_Movie";


    public ItemFavMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_item_fav_movie, container, false);
        textView = v.findViewById(R.id.no_movie);
        progressBar = v.findViewById(R.id.progress_Bar);
        rvFavMovie = v.findViewById(R.id.rv_fav_movie);
        setHasOptionsMenu(true);
        rvFavMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver observer = new DataObserver(handler, getContext());
        if (getActivity() != null){
            getActivity().getContentResolver().registerContentObserver(MOVIE_URI, true, observer);
        }
        movieAdapter = new MovieAdapter(getActivity());
        rvFavMovie.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickCallback(this::showSelectedMovie);

        if (savedInstanceState == null){
            new LoadMovieAsync(getContext(), this).execute();
        } else {
            ArrayList<ModelMovie> m = savedInstanceState.getParcelableArrayList(EXTRA_STATE_Movie);
            if (m != null){
                movieAdapter.setData(m);
            }
        }
        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE_Movie, movieAdapter.getListData());
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMovieAsync(getContext(), this).execute();
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
        ArrayList<ModelMovie> modelMovie = mapCursorToArrayListMovie(cursor);
        if (modelMovie.size() > 0){
            noCollection(false);
            movieAdapter.setData(modelMovie);
        } else {
            rvFavMovie.setVisibility(View.INVISIBLE);
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

    private void showSelectedMovie(ModelMovie modelMovie) {
        Toast.makeText(getContext(), modelMovie.getMovie_name(), Toast.LENGTH_SHORT).show();
    }

    private static class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<CatalogueCallback> weakCallback;

        private LoadMovieAsync(Context context, CatalogueCallback callback) {
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
            return context.getContentResolver().query(MOVIE_URI, null, null, null, null);
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
