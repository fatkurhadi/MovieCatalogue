package com.example.moviecatalogue.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.moviecatalogue.database.DBContract.ItemContract.*;

public class DBHelper extends SQLiteOpenHelper {

    private static final String dbNAME = "db_fav";
    private static final int dbVERSION = 4;

    private static final String CREATE_MOVIE = "create table "+DBContract.MOVIE_TABLE+ " ( "+
            ITEM_ID+" number primary key, "+
            ITEM_NAME+" text, "+
            ITEM_DATE+" text, "+
            ITEM_SCORE+" text, "+
            ITEM_SCORE_AVERAGE+" text, "+
            ITEM_POPULARITY+" text, "+
            ITEM_SYNOPSIS+" text, "+
            ITEM_POSTER+" text, "+
            ITEM_BACKDROP+" text)";

    private static final String CREATE_tvSHOW = "create table "+DBContract.tvSHOW_TABLE + " ( "+
            ITEM_ID+" number primary key, "+
            ITEM_NAME+" text, "+
            ITEM_DATE+" text, "+
            ITEM_SCORE+" text, "+
            ITEM_SCORE_AVERAGE+" text, "+
            ITEM_POPULARITY+" text, "+
            ITEM_SYNOPSIS+" text, "+
            ITEM_POSTER+" text, "+
            ITEM_BACKDROP+" text)";

    public DBHelper(Context context){
        super(context, dbNAME,null, dbVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVIE);
        db.execSQL(CREATE_tvSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+DBContract.MOVIE_TABLE);
        db.execSQL("drop table if exists "+DBContract.tvSHOW_TABLE);
        onCreate(db);
    }
}
