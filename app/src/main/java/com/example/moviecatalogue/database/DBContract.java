package com.example.moviecatalogue.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DBContract {
    public static final String AUTHORITY = "com.example.moviecatalogue";
    private static final String SCHEME = "content";
    public static final String MOVIE_TABLE = "movie_table";
    public static final String tvSHOW_TABLE = "tv_show_table";

    public static final Uri MOVIE_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(MOVIE_TABLE)
            .build();

    public static final Uri tvSHOW_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(tvSHOW_TABLE)
            .build();

    private DBContract(){}

    public static final class ItemContract implements BaseColumns {
        public static final String ITEM_ID = "item_id";
        public static final String ITEM_NAME = "item_name";
        public static final String ITEM_DATE = "item_date";
        public static final String ITEM_SCORE = "item_score";
        public static final String ITEM_SCORE_AVERAGE = "item_score_average";
        public static final String ITEM_POPULARITY = "item_popularity";
        public static final String ITEM_SYNOPSIS = "item_synopsis";
        public static final String ITEM_POSTER = "item_poster";
        public static final String ITEM_BACKDROP = "item_backdrop";
    }

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
}
