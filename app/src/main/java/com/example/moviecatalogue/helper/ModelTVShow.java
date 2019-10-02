package com.example.moviecatalogue.helper;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.moviecatalogue.database.DBContract;

import static com.example.moviecatalogue.database.DBContract.getColumnString;

public class ModelTVShow implements Parcelable {
    private String tvshow_id;
    private String tvshow_poster;
    private String tvshow_backdrop;
    private String tvshow_name;
    private String tvshow_synopsis;
    private String tvshow_date;
    private String tvshow_score;
    private String tvshow_score_average;
    private String tvshow_popularity;

    public ModelTVShow(String tvshow_id, String tvshow_name, String tvshow_date, String tvshow_score, String tvshow_score_average, String tvshow_popularity, String tvshow_synopsis, String tvshow_poster, String tvshow_backdrop) {
        this.tvshow_id = tvshow_id;
        this.tvshow_name = tvshow_name;
        this.tvshow_date = tvshow_date;
        this.tvshow_score = tvshow_score;
        this.tvshow_score_average = tvshow_score_average;
        this.tvshow_popularity = tvshow_popularity;
        this.tvshow_synopsis = tvshow_synopsis;
        this.tvshow_poster = tvshow_poster;
        this.tvshow_backdrop = tvshow_backdrop;
    }

    public ModelTVShow(Cursor cursor){
        this.tvshow_id = getColumnString(cursor, DBContract.ItemContract.ITEM_ID);
        this.tvshow_name = getColumnString(cursor, DBContract.ItemContract.ITEM_NAME);
        this.tvshow_date = getColumnString(cursor, DBContract.ItemContract.ITEM_DATE);
        this.tvshow_score = getColumnString(cursor, DBContract.ItemContract.ITEM_SCORE);
        this.tvshow_score_average = getColumnString(cursor, DBContract.ItemContract.ITEM_SCORE_AVERAGE);
        this.tvshow_popularity = getColumnString(cursor, DBContract.ItemContract.ITEM_POPULARITY);
        this.tvshow_synopsis = getColumnString(cursor, DBContract.ItemContract.ITEM_SYNOPSIS);
        this.tvshow_poster = getColumnString(cursor, DBContract.ItemContract.ITEM_POSTER);
        this.tvshow_backdrop = getColumnString(cursor, DBContract.ItemContract.ITEM_BACKDROP);
    }

    public String getTvshow_id() {
        return tvshow_id;
    }

    public void setTvshow_id(String tvshow_id) {
        this.tvshow_id = tvshow_id;
    }

    public String getTvshow_poster() {
        return tvshow_poster;
    }

    public void setTvshow_poster(String tvshow_poster) {
        this.tvshow_poster = tvshow_poster;
    }

    public String getTvshow_backdrop() {
        return tvshow_backdrop;
    }

    public void setTvshow_backdrop(String tvshow_backdrop) {
        this.tvshow_backdrop = tvshow_backdrop;
    }

    public String getTvshow_name() {
        return tvshow_name;
    }

    public void setTvshow_name(String tvshow_name) {
        this.tvshow_name = tvshow_name;
    }

    public String getTvshow_synopsis() {
        return tvshow_synopsis;
    }

    public void setTvshow_synopsis(String tvshow_synopsis) {
        this.tvshow_synopsis = tvshow_synopsis;
    }

    public String getTvshow_date() {
        return tvshow_date;
    }

    public void setTvshow_date(String tvshow_date) {
        this.tvshow_date = tvshow_date;
    }

    public String getTvshow_score() {
        return tvshow_score;
    }

    public void setTvshow_score(String tvshow_score) {
        this.tvshow_score = tvshow_score;
    }

    public String getTvshow_score_average() {
        return tvshow_score_average;
    }

    public void setTvshow_score_average(String tvshow_score_average) {
        this.tvshow_score_average = tvshow_score_average;
    }

    public String getTvshow_popularity() {
        return tvshow_popularity;
    }

    public void setTvshow_popularity(String tvshow_popularity) {
        this.tvshow_popularity = tvshow_popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tvshow_id);
        dest.writeString(this.tvshow_poster);
        dest.writeString(this.tvshow_backdrop);
        dest.writeString(this.tvshow_name);
        dest.writeString(this.tvshow_synopsis);
        dest.writeString(this.tvshow_date);
        dest.writeString(this.tvshow_score);
        dest.writeString(this.tvshow_score_average);
        dest.writeString(this.tvshow_popularity);
    }

    public ModelTVShow() {
    }

    protected ModelTVShow(Parcel in) {
        this.tvshow_id = in.readString();
        this.tvshow_poster = in.readString();
        this.tvshow_backdrop = in.readString();
        this.tvshow_name = in.readString();
        this.tvshow_synopsis = in.readString();
        this.tvshow_date = in.readString();
        this.tvshow_score = in.readString();
        this.tvshow_score_average = in.readString();
        this.tvshow_popularity = in.readString();
    }

    public static final Creator<ModelTVShow> CREATOR = new Creator<ModelTVShow>() {
        @Override
        public ModelTVShow createFromParcel(Parcel source) {
            return new ModelTVShow(source);
        }

        @Override
        public ModelTVShow[] newArray(int size) {
            return new ModelTVShow[size];
        }
    };
}
