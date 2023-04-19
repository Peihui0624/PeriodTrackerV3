package my.edu.utar.periodtracker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import my.edu.utar.periodtracker.R;

public class ReminderBroadcast extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {

        // Create a notification intent to be shown when the notification is clicked
        Intent notificationIntent = new Intent(context, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyPeriod")
                .setContentTitle("Reminder")
                .setContentText("Remember to sleep on time and get hydrated.")
                .setSmallIcon(R.drawable.ic_baseline_notification_important_24)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
