package com.example.moviecatalogue.helper;

import android.database.Cursor;

import java.util.ArrayList;

import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_BACKDROP;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_DATE;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_ID;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_NAME;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_POPULARITY;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_POSTER;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_SCORE;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_SCORE_AVERAGE;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_SYNOPSIS;

public class MappingCatalogue {
    public static ArrayList<ModelMovie> mapCursorToArrayListMovie(Cursor cursor){
        ArrayList<ModelMovie> modelMovies = new ArrayList<>();
        while (cursor.moveToNext()){
            String movie_id = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_ID));
            String movie_name = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_NAME));
            String movie_date = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_DATE));
            String movie_score = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_SCORE));
            String movie_score_average = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_SCORE_AVERAGE));
            String movie_popularity = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_POPULARITY));
            String movie_synopsis = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_SYNOPSIS));
            String movie_poster = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_POSTER));
            String movie_backdrop = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_BACKDROP));
            modelMovies.add(new ModelMovie(movie_id, movie_name, movie_date, movie_score, movie_score_average, movie_popularity, movie_synopsis, movie_poster, movie_backdrop));
        }
        return modelMovies;
    }

    public static ArrayList<ModelTVShow> mapCursorToArrayListTVShow(Cursor cursor){
        ArrayList<ModelTVShow> modelTvShows = new ArrayList<>();
        while (cursor.moveToNext()){
            String tvShow_id = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_ID));
            String tvShow_name = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_NAME));
            String tvShow_date = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_DATE));
            String tvShow_score = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_SCORE));
            String tvShow_score_average = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_SCORE_AVERAGE));
            String tvShow_popularity = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_POPULARITY));
            String tvShow_synopsis = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_SYNOPSIS));
            String tvShow_poster = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_POSTER));
            String tvShow_backdrop = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_BACKDROP));
            modelTvShows.add(new ModelTVShow(tvShow_id, tvShow_name, tvShow_date, tvShow_score, tvShow_score_average, tvShow_popularity, tvShow_synopsis, tvShow_poster, tvShow_backdrop));
        }
        return modelTvShows;
    }
}
