package com.example.moviecatalogue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.moviecatalogue.reminder.NotifyDaily;
import com.example.moviecatalogue.reminder.NotifyPref;
import com.example.moviecatalogue.reminder.NotifyRelease;

public class AppSettingActivity extends AppCompatActivity {

    private NotifyPref notifyPref;
    private Switch swDaily, swRelease;
    private NotifyDaily notifyDaily = new NotifyDaily();
    private NotifyRelease notifyRelease = new NotifyRelease();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);
        notifyPref = new NotifyPref(this);
        boolean notifyDaily = notifyPref.getDayNotify();
        boolean notifyRelease = notifyPref.getReNotify();
        swDaily = findViewById(R.id.sw_daily);
        swRelease = findViewById(R.id.sw_release);
        swDaily.setChecked(notifyDaily);
        swRelease.setChecked(notifyRelease);
        swDaily.setOnClickListener(dayListener);
        swRelease.setOnClickListener(reListener);

        if (swDaily.isChecked()){
            ImageView imageNotifyDaily = findViewById(R.id.img_daily);
            imageNotifyDaily.setImageDrawable(getResources().getDrawable(R.drawable.ic_notifications_active));
        }else {
            ImageView imageNotifyDaily = findViewById(R.id.img_daily);
            imageNotifyDaily.setImageDrawable(getResources().getDrawable(R.drawable.ic_notifications_off));
        }

        if (swRelease.isChecked()){
            ImageView imageNotifyRelease = findViewById(R.id.img_release);
            imageNotifyRelease.setImageDrawable(getResources().getDrawable(R.drawable.ic_notifications_active));
        }else {
            ImageView imageNotifyRelease = findViewById(R.id.img_release);
            imageNotifyRelease.setImageDrawable(getResources().getDrawable(R.drawable.ic_notifications_off));
        }
    }

    private View.OnClickListener dayListener = new View.OnClickListener() {
        @Override
        public void onClick(View v_day) {
            notifyPref.setDayNotify(swDaily.isChecked());
            boolean notifyDaily = notifyPref.getDayNotify();
            if (notifyDaily){
                AppSettingActivity.this.notifyDaily.DailyOn(getApplicationContext());
                ImageView imageNotifyDaily = findViewById(R.id.img_daily);
                imageNotifyDaily.setImageDrawable(getResources().getDrawable(R.drawable.ic_notifications_active));
            }else {
                AppSettingActivity.this.notifyDaily.DailyOff(getApplicationContext());
                ImageView imageNotifyDaily = findViewById(R.id.img_daily);
                imageNotifyDaily.setImageDrawable(getResources().getDrawable(R.drawable.ic_notifications_off));
            }
        }
    };

    private View.OnClickListener reListener = new View.OnClickListener() {
        @Override
        public void onClick(View v_re) {
            notifyPref.setReNotify(swRelease.isChecked());
            boolean notifyRelease = notifyPref.getReNotify();
            if (notifyRelease){
                AppSettingActivity.this.notifyRelease.ReleaseOn(getApplicationContext());
                ImageView imageNotifyRelease = findViewById(R.id.img_release);
                imageNotifyRelease.setImageDrawable(getResources().getDrawable(R.drawable.ic_notifications_active));
            }else {
                AppSettingActivity.this.notifyRelease.ReleaseOff(getApplicationContext());
                ImageView imageNotifyRelease = findViewById(R.id.img_release);
                imageNotifyRelease.setImageDrawable(getResources().getDrawable(R.drawable.ic_notifications_off));
            }
        }
    };
}
