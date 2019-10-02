package com.example.moviecatalogue.reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.moviecatalogue.MainActivity;
import com.example.moviecatalogue.R;

import java.util.Calendar;

public class NotifyDaily extends BroadcastReceiver {

    private final int NOTIFY_ID = 1;

    public NotifyDaily(){
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        DailyNotify(context);
    }

    private void DailyNotify(Context context) {
        int ch_request = 404;
        String ch_id = "ch1";
        String ch_name = "dailyNotify";
        String ch_title = context.getString(R.string.daily_title);
        CharSequence ch_message = context.getString(R.string.daily_message);
        Intent ch_intent = new Intent(context, MainActivity.class);
        PendingIntent ch_pendingIntent = TaskStackBuilder.create(context)
                .addNextIntent(ch_intent)
                .getPendingIntent(ch_request,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager ch_notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder ch_builder = new NotificationCompat.Builder(context,ch_id)
                .setContentIntent(ch_pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_active)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_notifications_active))
                .setContentTitle(ch_title)
                .setContentText(ch_message)
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel ch_notify= new NotificationChannel(ch_id, ch_name,NotificationManager.IMPORTANCE_DEFAULT);
            ch_builder.setChannelId(ch_id);
            if(ch_notificationManager!=null){
                ch_notificationManager.createNotificationChannel(ch_notify);
            }
        }

        if(ch_notificationManager!=null){
            ch_notificationManager.notify(NOTIFY_ID, ch_builder.build());
        }
    }

    public void DailyOn(Context context){
        AlarmManager ch_alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent ch_intent = new Intent(context, NotifyDaily.class);
        PendingIntent ch_pendingIntent = PendingIntent.getBroadcast(context, NOTIFY_ID, ch_intent, 0);

        Calendar ch_calendar = Calendar.getInstance();
        ch_calendar.set(Calendar.HOUR_OF_DAY, 7);
        ch_calendar.set(Calendar.MINUTE, 0);
        ch_calendar.set(Calendar.SECOND, 0);

        if (ch_alarmManager != null) {
            ch_alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, ch_calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, ch_pendingIntent);
        }
    }

    public void DailyOff(Context context){
        AlarmManager ch_alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent ch_intent = new Intent(context, NotifyDaily.class);
        PendingIntent ch_pendingIntent = PendingIntent.getBroadcast(context, NOTIFY_ID, ch_intent, 0);
        ch_pendingIntent.cancel();
        if (ch_alarmManager != null) {
            ch_alarmManager.cancel(ch_pendingIntent);
        }
    }
}
