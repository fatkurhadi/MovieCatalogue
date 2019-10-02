package com.example.moviecatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.database.DBContract;
import com.example.moviecatalogue.database.DBHelper;
import com.example.moviecatalogue.helper.ModelMovie;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StackRemoteWidget implements RemoteViewsService.RemoteViewsFactory {
    private final ArrayList<ModelMovie> modelMovies = new ArrayList<>();
    private final Context context;
    private Cursor widgetCursor;

    StackRemoteWidget(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        loadData();
    }

    @Override
    public void onDataSetChanged() {
        loadData();
    }

    private void loadData() {
        modelMovies.clear();
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        widgetCursor = database.query(DBContract.MOVIE_TABLE, null, null, null, null, null, null);
        if (widgetCursor != null) {
            if (widgetCursor.moveToFirst()) {
                do {
                    ModelMovie modelMovie = new ModelMovie(widgetCursor);
                    modelMovies.add(modelMovie);
                } while (widgetCursor.moveToNext());
            }
        }
    }

    @Override
    public void onDestroy() {
        if (widgetCursor != null) {
            widgetCursor.close();
        }
        modelMovies.clear();
    }

    @Override
    public int getCount() {
        return modelMovies.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews widgetRemoteViews = new RemoteViews(context.getPackageName(), R.layout.item_list_widget);
        String BackdropUrl = modelMovies.get(position).getMovie_backdrop();
        String MovieName = modelMovies.get(position).getMovie_name();
        CharSequence MoviesName = modelMovies.get(position).getMovie_name();
        try {
            Bitmap bitmapImage = Glide.with(context)
                    .asBitmap()
                    .load(BackdropUrl)
                    .apply(new RequestOptions().centerCrop())
                    .submit()
                    .get();
            widgetRemoteViews.setImageViewBitmap(R.id.image_widget, bitmapImage);
            widgetRemoteViews.setTextViewText(R.id.text_widget, MoviesName);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        Bundle widgetBundle = new Bundle();
        widgetBundle.putString(FavMovieWidget.WIDGET_ITEM, MovieName);
        Intent widgetIntent = new Intent();
        widgetIntent.putExtras(widgetBundle);
        widgetRemoteViews.setOnClickFillInIntent(R.id.image_widget, widgetIntent);
        return widgetRemoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
