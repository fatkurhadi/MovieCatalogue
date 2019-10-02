package com.example.moviecatalogue.helper;

import android.database.Cursor;

public interface CatalogueCallback {
    void preExecute();
    void postExecute(Cursor cursor);
}
