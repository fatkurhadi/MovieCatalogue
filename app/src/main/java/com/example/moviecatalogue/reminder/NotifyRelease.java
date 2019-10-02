package com.example.moviecatalogue.reminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.moviecatalogue.BuildConfig;
import com.example.moviecatalogue.MainActivity;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.ReleaseTodayActivity;
import com.example.moviecatalogue.helper.ModelMovie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class NotifyRelease extends BroadcastReceiver {
    private final int NOTIFY_ID = 2;
    private ArrayList<ModelMovie> modelMovies = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        getRelease(context);
    }

    private void getRelease(Context context) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
        String today = day.format(new Date());

        AsyncHttpClient releasedClient = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+ BuildConfig.MY_API_KEY +"&primary_release_date.gte=" + today + "&primary_release_date.lte=" + today;
        releasedClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        if (today.equals(movie.getString("release_date"))) {
                            ModelMovie m = new ModelMovie();
                            String id = String.valueOf(movie.getInt("id"));
                            m.setMovie_id(id);
                            m.setMovie_name(movie.getString("title"));
                            m.setMovie_popularity(String.valueOf(movie.getDouble("popularity")));
                            m.setMovie_score(String.valueOf(movie.getDouble("vote_count")));
                            m.setMovie_score_average(String.valueOf(movie.getDouble("vote_average")));
                            m.setMovie_synopsis(movie.getString("overview"));
                            m.setMovie_poster("https://image.tmdb.org/t/p/original" + movie.getString("poster_path"));
                            m.setMovie_backdrop("https://image.tmdb.org/t/p/original" + movie.getString("backdrop_path"));
                            m.setMovie_date(movie.getString("release_date"));
                            modelMovies.add(m);
                        }
                    }
                    NotifyShow(context);
                } catch (Exception e) {
                    Log.d("Release Notify : ", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("UnableConnect : ", error.getMessage());
            }
        });
    }

    private void NotifyShow(Context context) {
        int ch_request = 404;
        String ch_id = "ch2";
        String ch_name = "releaseNotify";
        String ch_title = context.getString(R.string.release_title);
        String ch_message;
        Intent ch_intent;
        Bitmap bitmapImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notifications_active);
        NotificationManager ch_notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder ch_builder = new NotificationCompat.Builder(context, ch_id);

        if (modelMovies.size() > 0) {
            ch_intent = new Intent(context, ReleaseTodayActivity.class);
                PendingIntent ch_pendingIntent = TaskStackBuilder.create(context).addNextIntent(ch_intent).getPendingIntent(ch_request, PendingIntent.FLAG_UPDATE_CURRENT);
                ch_message = context.getString(R.string.release_message);
                ch_builder.setSmallIcon(R.drawable.ic_notifications_active)
                        .setLargeIcon(bitmapImage)
                        .setContentTitle(ch_title)
                        .setContentText(ch_message)
                        .setContentIntent(ch_pendingIntent)
                        .setAutoCancel(true);
                if (ch_notificationManager != null) {
                    ch_notificationManager.notify(NOTIFY_ID, ch_builder.build());
                }
        } else {
            ch_intent = new Intent(context, MainActivity.class);
            PendingIntent ch_pendingIntent = TaskStackBuilder.create(context).addNextIntent(ch_intent).getPendingIntent(ch_request, PendingIntent.FLAG_UPDATE_CURRENT);
            ch_message = context.getString(R.string.release_empty);
            ch_builder.setSmallIcon(R.drawable.ic_notifications_active)
                    .setLargeIcon(bitmapImage)
                    .setContentTitle(ch_title)
                    .setContentText(ch_message)
                    .setContentIntent(ch_pendingIntent)
                    .setAutoCancel(true);
            if (ch_notificationManager != null) {
                ch_notificationManager.notify(NOTIFY_ID, ch_builder.build());
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notify_channel = new NotificationChannel(ch_id, ch_name, NotificationManager.IMPORTANCE_DEFAULT);
            ch_builder.setChannelId(ch_id);
            if (ch_notificationManager != null) {
                ch_notificationManager.createNotificationChannel(notify_channel);
            }
        }
    }

    public void ReleaseOn(Context context){
        AlarmManager ch_alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent ch_intent = new Intent(context, NotifyRelease.class);

        Calendar ch_calendar = Calendar.getInstance();
        ch_calendar.set(Calendar.HOUR_OF_DAY, 8);
        ch_calendar.set(Calendar.MINUTE, 0);
        ch_calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFY_ID, ch_intent, 0);
        if (ch_alarmManager != null) {
            ch_alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, ch_calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void ReleaseOff(Context context){
        AlarmManager ch_alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent ch_intent = new Intent(context, NotifyRelease.class);
        PendingIntent ch_pendingIntent = PendingIntent.getBroadcast(context, NOTIFY_ID, ch_intent, 0);
        ch_pendingIntent.cancel();
        if (ch_alarmManager != null) {
            ch_alarmManager.cancel(ch_pendingIntent);
        }
    }
}
