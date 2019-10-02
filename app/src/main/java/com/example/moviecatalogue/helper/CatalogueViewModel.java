package com.example.moviecatalogue.helper;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.moviecatalogue.BuildConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class CatalogueViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.MY_API_KEY;
    private MutableLiveData<ArrayList<ModelMovie>> listMovie = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ModelTVShow>> listTVShow = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ModelMovie>> resultMovie = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ModelTVShow>> resultTVShow = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ModelMovie>> releasedToday = new MutableLiveData<>();

    public void setReleased(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
        String today = day.format(new Date());

        AsyncHttpClient releasedClient = new AsyncHttpClient();
        String releasedUrl = "https://api.themoviedb.org/3/discover/movie?api_key="+ BuildConfig.MY_API_KEY +"&primary_release_date.gte=" + today + "&primary_release_date.lte=" + today;
        final ArrayList<ModelMovie> listReleasedToday = new ArrayList<>();

        releasedClient.get(releasedUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String releasedResult = new String(responseBody);
                    JSONObject responseObject = new JSONObject(releasedResult);
                    JSONArray releasedList = responseObject.getJSONArray("results");

                    for (int i = 0; i < releasedList.length(); i++){
                        JSONObject released = releasedList.getJSONObject(i);
                        ModelMovie r = new ModelMovie();
                        r.setMovie_poster("https://image.tmdb.org/t/p/original" + released.getString("poster_path"));
                        r.setMovie_backdrop("https://image.tmdb.org/t/p/original" + released.getString("backdrop_path"));
                        r.setMovie_name(released.getString("title"));
                        r.setMovie_date(released.getString("release_date"));
                        r.setMovie_synopsis(released.getString("overview"));
                        r.setMovie_score(released.getString("vote_count"));
                        r.setMovie_score_average(released.getString("vote_average"));
                        r.setMovie_popularity(released.getString("popularity"));
                        r.setMovie_id(released.getString("id"));
                        listReleasedToday.add(r);
                    }
                    releasedToday.postValue(listReleasedToday);
                } catch (JSONException e) {
                    Log.d("ExceptionReleased", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailureReleased", error.getMessage());
            }
        });
    }
    public LiveData<ArrayList<ModelMovie>> getReleased() { return releasedToday;}

    public void setMovie(){
        AsyncHttpClient movieClient = new AsyncHttpClient();
        String movieUrl = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";
        final ArrayList<ModelMovie> listItemsModelMovies = new ArrayList<>();

        movieClient.get(movieUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String movieResult = new String(responseBody);
                    JSONObject responseObject = new JSONObject(movieResult);
                    JSONArray movieList = responseObject.getJSONArray("results");

                    for (int i = 0; i < movieList.length(); i++){
                        JSONObject movie = movieList.getJSONObject(i);
                        ModelMovie m = new ModelMovie();
                        m.setMovie_poster("https://image.tmdb.org/t/p/original" + movie.getString("poster_path"));
                        m.setMovie_backdrop("https://image.tmdb.org/t/p/original" + movie.getString("backdrop_path"));
                        m.setMovie_name(movie.getString("title"));
                        m.setMovie_date(movie.getString("release_date"));
                        m.setMovie_synopsis(movie.getString("overview"));
                        m.setMovie_score(movie.getString("vote_count"));
                        m.setMovie_score_average(movie.getString("vote_average"));
                        m.setMovie_popularity(movie.getString("popularity"));
                        m.setMovie_id(movie.getString("id"));
                        listItemsModelMovies.add(m);
                    }
                    listMovie.postValue(listItemsModelMovies);
                } catch (JSONException e) {
                    Log.d("ExceptionMovie", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailureMovie", error.getMessage());
            }
        });
    }
    public LiveData<ArrayList<ModelMovie>> getMovie() { return listMovie;}

    public void setTVShow(){
        AsyncHttpClient tvShowClient = new AsyncHttpClient();
        String tvShowUrl = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";
        final ArrayList<ModelTVShow> listItemsModelTVShows = new ArrayList<>();

        tvShowClient.get(tvShowUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String tvShowResult = new String(responseBody);
                    JSONObject responseObject = new JSONObject(tvShowResult);
                    JSONArray tvShowList = responseObject.getJSONArray("results");

                    for (int i = 0; i < tvShowList.length(); i++){
                        JSONObject tvShow = tvShowList.getJSONObject(i);
                        ModelTVShow tv = new ModelTVShow();
                        tv.setTvshow_poster("https://image.tmdb.org/t/p/original" + tvShow.getString("poster_path"));
                        tv.setTvshow_backdrop("https://image.tmdb.org/t/p/original" + tvShow.getString("backdrop_path"));
                        tv.setTvshow_name(tvShow.getString("original_name"));
                        tv.setTvshow_date(tvShow.getString("first_air_date"));
                        tv.setTvshow_synopsis(tvShow.getString("overview"));
                        tv.setTvshow_score(tvShow.getString("vote_count"));
                        tv.setTvshow_score_average(tvShow.getString("vote_average"));
                        tv.setTvshow_popularity(tvShow.getString("popularity"));
                        tv.setTvshow_id(tvShow.getString("id"));
                        listItemsModelTVShows.add(tv);
                    }
                    listTVShow.postValue(listItemsModelTVShows);
                } catch (JSONException e) {
                    Log.d("ExceptionTVShow", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailureTVShow", error.getMessage());
            }
        });
    }
    public LiveData<ArrayList<ModelTVShow>> getTVShow() { return listTVShow;}

    public  void  setResultMovies(String url){
        AsyncHttpClient resultMovieClient = new AsyncHttpClient();
        final ArrayList<ModelMovie> listItemsResultModelMovies = new ArrayList<>();
        resultMovieClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resultM = new String(responseBody);
                    JSONObject responseObject = new JSONObject(resultM);
                    JSONArray resultMovieList  = responseObject.getJSONArray("results");
                    for ( int i=0; i<resultMovieList.length(); i++){
                        JSONObject movie = resultMovieList.getJSONObject(i);
                        ModelMovie rm = new ModelMovie();
                        String idMovie = String.valueOf(movie.getInt("id"));
                        rm.setMovie_id(idMovie);
                        rm.setMovie_name(movie.getString("title"));
                        if(String.valueOf(movie.getDouble("popularity")).equals("")){
                            rm.setMovie_popularity("-");
                        }else {
                            rm.setMovie_popularity(String.valueOf(movie.getDouble("popularity")));
                        }
                        if(String.valueOf(movie.getDouble("vote_count")).equals("")){
                            rm.setMovie_score("-");
                        }else {
                            rm.setMovie_score(String.valueOf(movie.getDouble("vote_count")));
                        }
                        if(String.valueOf(movie.getDouble("vote_average")).equals("")){
                            rm.setMovie_score_average("-");
                        }else {
                            rm.setMovie_score_average(String.valueOf(movie.getDouble("vote_average")));
                        }
                        if(movie.getString("overview").equals("")){
                            rm.setMovie_synopsis("-");
                        }else {
                            rm.setMovie_synopsis(movie.getString("overview"));
                        }
                        if(movie.getString("poster_path").equals("")){
                            rm.setMovie_poster("-");
                        }else {
                            rm.setMovie_poster("https://image.tmdb.org/t/p/original" + movie.getString("poster_path"));
                        }
                        if(movie.getString("backdrop_path").equals("")){
                            rm.setMovie_backdrop("-");
                        }else {
                            rm.setMovie_backdrop("https://image.tmdb.org/t/p/original" + movie.getString("backdrop_path"));
                        }
                        if(movie.getString("release_date").equals("")){
                            rm.setMovie_date("-");
                        }else {
                            rm.setMovie_date(movie.getString("release_date"));
                        }
                        listItemsResultModelMovies.add(rm);
                    }
                    resultMovie.postValue(listItemsResultModelMovies);
                } catch (JSONException e) {
                    Log.d("Search ModelMovie : ", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailureSearchM : ", error.getMessage());
                listMovie.postValue(listItemsResultModelMovies);
            }
        });
    }

    public LiveData<ArrayList<ModelMovie>> getResultMovies(){
        return resultMovie;
    }

    public  void  setResultTVShow(String url){
        AsyncHttpClient resultTVShowClient = new AsyncHttpClient();
        final ArrayList<ModelTVShow> listItemsResultModelTVShows = new ArrayList<>();
        resultTVShowClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resultTV = new String(responseBody);
                    JSONObject responseObject = new JSONObject(resultTV);
                    JSONArray resultTVShowList  = responseObject.getJSONArray("results");
                    for ( int i=0; i<resultTVShowList.length(); i++){
                        JSONObject tvShow = resultTVShowList.getJSONObject(i);
                        ModelTVShow rtv = new ModelTVShow();
                        String idTVShow = String.valueOf(tvShow.getInt("id"));
                        rtv.setTvshow_id(idTVShow);
                        rtv.setTvshow_name(tvShow.getString("original_name"));
                        if(String.valueOf(tvShow.getDouble("popularity")).equals("")){
                            rtv.setTvshow_popularity("-");
                        }else {
                            rtv.setTvshow_popularity(String.valueOf(tvShow.getDouble("popularity")));
                        }
                        if(String.valueOf(tvShow.getDouble("vote_count")).equals("")){
                            rtv.setTvshow_score("-");
                        }else {
                            rtv.setTvshow_score(String.valueOf(tvShow.getDouble("vote_count")));
                        }
                        if(String.valueOf(tvShow.getDouble("vote_average")).equals("")){
                            rtv.setTvshow_score_average("-");
                        }else {
                            rtv.setTvshow_score_average(String.valueOf(tvShow.getDouble("vote_average")));
                        }
                        if(tvShow.getString("overview").equals("")){
                            rtv.setTvshow_synopsis("-");
                        }else {
                            rtv.setTvshow_synopsis(tvShow.getString("overview"));
                        }
                        if(tvShow.getString("poster_path").equals("")){
                            rtv.setTvshow_poster("-");
                        }else {
                            rtv.setTvshow_poster("https://image.tmdb.org/t/p/original" + tvShow.getString("poster_path"));
                        }
                        if(tvShow.getString("backdrop_path").equals("")){
                            rtv.setTvshow_backdrop("-");
                        }else {
                            rtv.setTvshow_backdrop("https://image.tmdb.org/t/p/original" + tvShow.getString("backdrop_path"));
                        }
                        if(tvShow.getString("first_air_date").equals("")){
                            rtv.setTvshow_date("-");
                        }else {
                            rtv.setTvshow_date(tvShow.getString("first_air_date"));
                        }
                        listItemsResultModelTVShows.add(rtv);
                    }
                    resultTVShow.postValue(listItemsResultModelTVShows);
                } catch (JSONException e) {
                    Log.d("Search ModelTVShow : ", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailureSearchTV : ", error.getMessage());
                listTVShow.postValue(listItemsResultModelTVShows);
            }
        });
    }

    public LiveData<ArrayList<ModelTVShow>> getResultTVShow(){
        return resultTVShow;
    }
}
