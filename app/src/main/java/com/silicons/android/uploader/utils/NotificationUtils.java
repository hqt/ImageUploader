package com.silicons.android.uploader.utils;

import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.silicons.android.uploader.R;

/**
 * Created by Huynh Quang Thao on 1/4/16.
 */
public class NotificationUtils {
    public static void run(Context context, int notificationId, String title, String content) {

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setColor(Color.BLUE);


        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}






