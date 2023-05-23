package com.wgu.termplanner;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.codepath.example.alarm.ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Here, you can add the code to create a notification
    }

}
