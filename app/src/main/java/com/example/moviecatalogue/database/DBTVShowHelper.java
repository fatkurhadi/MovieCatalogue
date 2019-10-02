package com.example.moviecatalogue.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.moviecatalogue.database.DBContract.ItemContract.ITEM_ID;
import static com.example.moviecatalogue.database.DBContract.tvSHOW_TABLE;

public class DBTVShowHelper {
    private static final String TVSHOW_TB = tvSHOW_TABLE;
    private static DBHelper dbHelper;
    private static DBTVShowHelper INSTANCE;

    private static SQLiteDatabase database;

    private DBTVShowHelper(Context context){
        dbHelper = new DBHelper(context);
    }

    public static DBTVShowHelper getINSTANCE(Context context) {
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new DBTVShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor selectByIdProvider(String id){
        return database.query(TVSHOW_TB, null, ITEM_ID + " = ?", new String[]{id}, null, null, null, null);
    }

    public Cursor selectProvider(){
        return database.query(TVSHOW_TB, null, null, null, null, null, ITEM_ID + " ASC", null);
    }

    public long insertProvider(ContentValues values){
        return database.insert(TVSHOW_TB, null, values);
    }

    public int deleteProvider(String id){
        return database.delete(TVSHOW_TB, ITEM_ID + " = ?", new String[]{id});
    }
}
