package com.example.moviecatalogue.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.moviecatalogue.R;

/**
 * Implementation of App Widget functionality.
 */
public class FavMovieWidget extends AppWidgetProvider {
    private static final String WIDGET_ACTION = "com.example.moviecatalogue.WIDGETACTION";
    public static final String WIDGET_ITEM = "com.example.moviecatalogue.WIDGETITEM";

    static void WidgetUpdate(Context context, AppWidgetManager widgetManager,
                                int widgetId) {
        Intent widgetIntent = new Intent(context, WidgetService.class);
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        widgetIntent.setData(Uri.parse(widgetIntent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews widgetRemoteViews = new RemoteViews(context.getPackageName(), R.layout.fav_movie_widget);
        widgetRemoteViews.setRemoteAdapter(R.id.sv_widget, widgetIntent);
        widgetRemoteViews.setEmptyView(R.id.sv_widget, R.id.empty_widget);

        Intent widgetToastIntent = new Intent(context, FavMovieWidget.class);
        widgetToastIntent.setAction(FavMovieWidget.WIDGET_ACTION);
        widgetToastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        widgetToastIntent.setData(Uri.parse(widgetIntent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent widgetPendingIntent = PendingIntent.getBroadcast(context, 0, widgetToastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        widgetRemoteViews.setPendingIntentTemplate(R.id.sv_widget, widgetPendingIntent);
        widgetManager.updateAppWidget(widgetId, widgetRemoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager widgetManager, int[] widgetIds) {
        for (int widgetId : widgetIds) {
            WidgetUpdate(context, widgetManager, widgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent widgetIntent) {
        super.onReceive(context, widgetIntent);
        if (widgetIntent.getAction() != null) {
            if (widgetIntent.getAction().equals(WIDGET_ACTION)){
                String movieName = widgetIntent.getStringExtra(WIDGET_ITEM);
                Toast.makeText(context, movieName, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

