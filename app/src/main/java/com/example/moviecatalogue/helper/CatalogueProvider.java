package com.example.moviecatalogue.helper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.moviecatalogue.database.DBMovieHelper;
import com.example.moviecatalogue.database.DBTVShowHelper;
import com.example.moviecatalogue.fragment.ItemFavMovieFragment;
import com.example.moviecatalogue.fragment.ItemFavTVShowFragment;

import static com.example.moviecatalogue.database.DBContract.AUTHORITY;
import static com.example.moviecatalogue.database.DBContract.MOVIE_TABLE;
import static com.example.moviecatalogue.database.DBContract.MOVIE_URI;
import static com.example.moviecatalogue.database.DBContract.tvSHOW_TABLE;
import static com.example.moviecatalogue.database.DBContract.tvSHOW_URI;

public class CatalogueProvider extends ContentProvider {
    private static final int MOVIE = 109;
    private static final int id_MOVIE = 208;
    private static final int tvSHOW = 307;
    private static final int id_tvSHOW = 406;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DBMovieHelper dbMovieHelper;
    private DBTVShowHelper dbtvShowHelper;

    static {
        uriMatcher.addURI(AUTHORITY, MOVIE_TABLE, MOVIE);
        uriMatcher.addURI(AUTHORITY, MOVIE_TABLE + "/#", id_MOVIE);
        uriMatcher.addURI(AUTHORITY, tvSHOW_TABLE, tvSHOW);
        uriMatcher.addURI(AUTHORITY, tvSHOW_TABLE + "/#", id_tvSHOW);
    }

    @Override
    public boolean onCreate() {
        dbMovieHelper = DBMovieHelper.getINSTANCE(getContext());
        dbtvShowHelper = DBTVShowHelper.getINSTANCE(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        dbMovieHelper.open();
        dbtvShowHelper.open();
        Cursor cursor;
        switch (uriMatcher.match(uri)){
            case MOVIE:
                cursor = dbMovieHelper.selectProvider();
                break;
            case id_MOVIE:
                cursor = dbMovieHelper.selectByIdProvider(uri.getLastPathSegment());
                break;
            case tvSHOW:
                cursor = dbtvShowHelper.selectProvider();
                break;
            case id_tvSHOW:
                cursor = dbtvShowHelper.selectByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri uriProvider;
        dbMovieHelper.open();
        dbtvShowHelper.open();
        long added;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                added = dbMovieHelper.insertProvider(values);
                uriProvider = Uri.parse(MOVIE_URI + "/" + added);
                if (getContext() != null){
                    getContext().getContentResolver().notifyChange(MOVIE_URI, new ItemFavMovieFragment.DataObserver(new Handler(), getContext()));
                }
                break;
            case tvSHOW:
                added = dbtvShowHelper.insertProvider(values);
                uriProvider = Uri.parse(tvSHOW_URI + "/" + added);
                if (getContext() != null){
                    getContext().getContentResolver().notifyChange(tvSHOW_URI, new ItemFavTVShowFragment.DataObserver(new Handler(), getContext()));
                }
                break;
            default:
                throw new SQLException("Failed insert " + uri);
        }
        return uriProvider;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        dbMovieHelper.open();
        dbtvShowHelper.open();
        switch (uriMatcher.match(uri)){
            case id_MOVIE:
                deleted = dbMovieHelper.deleteProvider(uri.getLastPathSegment());
                if (getContext() != null){
                    getContext().getContentResolver().notifyChange(MOVIE_URI, new ItemFavMovieFragment.DataObserver(new Handler(), getContext()));
                }
                break;
            case id_tvSHOW:
                deleted = dbtvShowHelper.deleteProvider(uri.getLastPathSegment());
                if (getContext() != null){
                    getContext().getContentResolver().notifyChange(tvSHOW_URI, new ItemFavTVShowFragment.DataObserver(new Handler(), getContext()));
                }
                break;
            default:
                deleted = 0;
                break;
        }
        return deleted ;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
