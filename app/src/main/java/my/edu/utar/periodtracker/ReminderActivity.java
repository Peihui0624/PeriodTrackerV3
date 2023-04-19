package my.edu.utar.periodtracker;

import android.accessibilityservice.AccessibilityService;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;

import my.edu.utar.periodtracker.R;

public class ReminderActivity extends AppCompatActivity {
    private NotificationManager notificationManager;
    private static final int NOTIFICATION_ID_SLEEP_REMINDER = 1;
    private static final int NOTIFICATION_ID_DRINK_REMINDER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        // Find the Switch by its ID
        Switch switchNotification = findViewById(R.id.switchNotification);
        // Get the NotificationManager instance
        notificationManager = (NotificationManager) getSystemService(ReminderActivity.NOTIFICATION_SERVICE);

        // Set an OnCheckedChangeListener for the Switch
        switchNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Toggle notifications based on switch state
            if (isChecked) {
                Toast.makeText(ReminderActivity.this, "Reminder is allowed!", Toast.LENGTH_SHORT).show();
                createNotificationChannel();
                enableNotifications();
            } else {
                disableNotifications();
            }
        });
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notifyPeriod", "Notify Sleep Drink", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Sleep and Drink");
            NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void enableNotifications() {
        // Create notification channels if running on Android Oreo or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel("channel1", "Channel 1", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel1);
        }

        // Get the current time
        Calendar calendar = Calendar.getInstance();

        // Set the time for sleep reminder notification (e.g. 10:00 PM)
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 23);
        calendar.set(Calendar.SECOND, 0);
        long sleepReminderTime = calendar.getTimeInMillis();

        // Schedule the sleep reminder notification
        Intent sleepReminderIntent = new Intent(this, ReminderBroadcast.class);
        PendingIntent sleepReminderPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID_SLEEP_REMINDER, sleepReminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, sleepReminderTime, sleepReminderPendingIntent);

    }

    private void disableNotifications() {
        // Cancel sleep reminder notification
        Intent sleepReminderIntent = new Intent(this, ReminderBroadcast.class);
        PendingIntent sleepReminderPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID_SLEEP_REMINDER, sleepReminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(sleepReminderPendingIntent);

    }

}