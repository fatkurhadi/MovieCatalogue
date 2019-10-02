package com.example.moviecatalogue.reminder;

import android.content.Context;
import android.content.SharedPreferences;

public class NotifyPref {

    private static final String notifyPref = "notify_pref";
    private String dailyPref = "daily_Pref";
    private String releasePref = "release_Pref";

    private Context context;
    private SharedPreferences sharedPref;

    public NotifyPref(Context context) {
        this.context = context;
    }

    public void setDayNotify(Boolean value) {
        sharedPref = context.getSharedPreferences(notifyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(dailyPref, value);
        editor.apply();
    }

    public boolean getDayNotify() {
        sharedPref = context.getSharedPreferences(notifyPref, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(dailyPref, false);
    }

    public void setReNotify(Boolean value) {
        sharedPref = context.getSharedPreferences(notifyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(releasePref, value);
        editor.apply();
    }

    public boolean getReNotify() {
        sharedPref = context.getSharedPreferences(notifyPref, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(releasePref, false);
    }
}
