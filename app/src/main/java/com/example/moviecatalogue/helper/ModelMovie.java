package com.example.moviecatalogue.helper;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.moviecatalogue.database.DBContract;

import static com.example.moviecatalogue.database.DBContract.getColumnString;

public class ModelMovie implements Parcelable {
    private String movie_id;
    private String movie_poster;
    private String movie_backdrop;
    private String movie_name;
    private String movie_synopsis;
    private String movie_date;
    private String movie_score;
    private String movie_score_average;
    private String movie_popularity;

    public ModelMovie(String movie_id, String movie_name, String movie_date, String movie_score, String movie_score_average, String movie_popularity, String movie_synopsis, String movie_poster, String movie_backdrop) {
        this.movie_id = movie_id;
        this.movie_name = movie_name;
        this.movie_date = movie_date;
        this.movie_score = movie_score;
        this.movie_score_average = movie_score_average;
        this.movie_popularity = movie_popularity;
        this.movie_synopsis = movie_synopsis;
        this.movie_poster = movie_poster;
        this.movie_backdrop = movie_backdrop;
    }

    public ModelMovie(Cursor cursor){
        this.movie_id = getColumnString(cursor, DBContract.ItemContract.ITEM_ID);
        this.movie_name = getColumnString(cursor, DBContract.ItemContract.ITEM_NAME);
        this.movie_date = getColumnString(cursor, DBContract.ItemContract.ITEM_DATE);
        this.movie_score = getColumnString(cursor, DBContract.ItemContract.ITEM_SCORE);
        this.movie_score_average = getColumnString(cursor, DBContract.ItemContract.ITEM_SCORE_AVERAGE);
        this.movie_popularity = getColumnString(cursor, DBContract.ItemContract.ITEM_POPULARITY);
        this.movie_synopsis = getColumnString(cursor, DBContract.ItemContract.ITEM_SYNOPSIS);
        this.movie_poster = getColumnString(cursor, DBContract.ItemContract.ITEM_POSTER);
        this.movie_backdrop = getColumnString(cursor, DBContract.ItemContract.ITEM_BACKDROP);
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_poster() {
        return movie_poster;
    }

    public void setMovie_poster(String movie_poster) {
        this.movie_poster = movie_poster;
    }

    public String getMovie_backdrop() {
        return movie_backdrop;
    }

    public void setMovie_backdrop(String movie_backdrop) {
        this.movie_backdrop = movie_backdrop;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getMovie_synopsis() {
        return movie_synopsis;
    }

    public void setMovie_synopsis(String movie_synopsis) {
        this.movie_synopsis = movie_synopsis;
    }

    public String getMovie_date() {
        return movie_date;
    }

    public void setMovie_date(String movie_date) {
        this.movie_date = movie_date;
    }

    public String getMovie_score() {
        return movie_score;
    }

    public void setMovie_score(String movie_score) {
        this.movie_score = movie_score;
    }

    public String getMovie_score_average() {
        return movie_score_average;
    }

    public void setMovie_score_average(String movie_score_average) {
        this.movie_score_average = movie_score_average;
    }

    public String getMovie_popularity() {
        return movie_popularity;
    }

    public void setMovie_popularity(String movie_popularity) {
        this.movie_popularity = movie_popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.movie_id);
        dest.writeString(this.movie_poster);
        dest.writeString(this.movie_backdrop);
        dest.writeString(this.movie_name);
        dest.writeString(this.movie_synopsis);
        dest.writeString(this.movie_date);
        dest.writeString(this.movie_score);
        dest.writeString(this.movie_score_average);
        dest.writeString(this.movie_popularity);
    }

    public ModelMovie() {
    }

    protected ModelMovie(Parcel in) {
        this.movie_id = in.readString();
        this.movie_poster = in.readString();
        this.movie_backdrop = in.readString();
        this.movie_name = in.readString();
        this.movie_synopsis = in.readString();
        this.movie_date = in.readString();
        this.movie_score = in.readString();
        this.movie_score_average = in.readString();
        this.movie_popularity = in.readString();
    }

    public static final Creator<ModelMovie> CREATOR = new Creator<ModelMovie>() {
        @Override
        public ModelMovie createFromParcel(Parcel source) {
            return new ModelMovie(source);
        }

        @Override
        public ModelMovie[] newArray(int size) {
            return new ModelMovie[size];
        }
    };
}
