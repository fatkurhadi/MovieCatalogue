package com.example.moviecatalogue.helper;

import android.content.ContentValues;

import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_BACKDROP;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_DATE;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_ID;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_NAME;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_POPULARITY;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_POSTER;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_SCORE;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_SCORE_AVERAGE;
import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_SYNOPSIS;

public class ContentValueCatalogue {

    public static ContentValues getContentValueMovie(ModelMovie modelMovie){
        ContentValues values = new ContentValues();
        values.put(ITEM_ID, modelMovie.getMovie_id());
        values.put(ITEM_NAME, modelMovie.getMovie_name());
        values.put(ITEM_DATE, modelMovie.getMovie_date());
        values.put(ITEM_SCORE, modelMovie.getMovie_score());
        values.put(ITEM_SCORE_AVERAGE, modelMovie.getMovie_score_average());
        values.put(ITEM_POPULARITY, modelMovie.getMovie_popularity());
        values.put(ITEM_SYNOPSIS, modelMovie.getMovie_synopsis());
        values.put(ITEM_POSTER, modelMovie.getMovie_poster());
        values.put(ITEM_BACKDROP, modelMovie.getMovie_backdrop());
        return values;
    }

    public static ContentValues getContentValueTVShow(ModelTVShow modelTvShow){
        ContentValues values = new ContentValues();
        values.put(ITEM_ID, modelTvShow.getTvshow_id());
        values.put(ITEM_NAME, modelTvShow.getTvshow_name());
        values.put(ITEM_DATE, modelTvShow.getTvshow_date());
        values.put(ITEM_SCORE, modelTvShow.getTvshow_score());
        values.put(ITEM_SCORE_AVERAGE, modelTvShow.getTvshow_score_average());
        values.put(ITEM_POPULARITY, modelTvShow.getTvshow_popularity());
        values.put(ITEM_SYNOPSIS, modelTvShow.getTvshow_synopsis());
        values.put(ITEM_POSTER, modelTvShow.getTvshow_poster());
        values.put(ITEM_BACKDROP, modelTvShow.getTvshow_backdrop());
        return values;
    }
}
