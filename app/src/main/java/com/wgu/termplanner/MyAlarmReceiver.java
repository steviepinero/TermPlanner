package com.wgu.termplanner;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.wgu.termplanner.alarm.ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context, intent.getStringExtra("courseName"));
    }

    private void createNotification(Context context, String courseName) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "COURSE_CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Course Alert")
/*
                .setContentText("The course " + courseName + " is starting/ending today!")
*/
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1001, builder.build());
    }

}

