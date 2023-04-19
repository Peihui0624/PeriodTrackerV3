package my.edu.utar.periodtracker;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SleepTrackerActivity extends AppCompatActivity {

    Date date = new Date();
    String email;
    DatabaseHelper databaseHelper;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_tracker);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        try{
            date = sdf.parse(intent.getStringExtra("date"));
        }catch (Exception e){

        }
        databaseHelper = new DatabaseHelper(this);

        TimePicker sleepTimePicker = findViewById(R.id.sleepTimePicker);
        TimePicker awakeTimePicker = findViewById(R.id.awakeTimePicker);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
//        sleepTimePicker.setHour(hour);
//        sleepTimePicker.setMinute(minute);
//        awakeTimePicker.setHour(hour);
//        awakeTimePicker.setMinute(minute);

        ImageView backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button saveButton = findViewById(R.id.btnSave);
        saveButton.setOnClickListener(v -> {
            int sleepHour = sleepTimePicker.getCurrentHour();
            int sleepMinute = sleepTimePicker.getCurrentMinute();
            int awakeHour = awakeTimePicker.getCurrentHour();
            int awakeMinute = awakeTimePicker.getCurrentMinute();
            int totalSleepMinutes = sleepHour * 60 + sleepMinute;
            int totalAwakeMinutes = awakeHour * 60 + awakeMinute;
            int year = date.getYear();
            int month = date.getMonth();
            int day = date.getDate();

            Calendar sleepTime = Calendar.getInstance();
            sleepTime.set(year, month, day, sleepHour, sleepMinute);

            Calendar awakeTime = Calendar.getInstance();
            awakeTime.set(year, month, day, awakeHour, awakeMinute);

//            int durationMinutes = totalAwakeMinutes - totalSleepMinutes;
//
//            if (durationMinutes < 0) {
//                durationMinutes += 24 * 60;
//            }
//
//            int durationHours = durationMinutes / 60;
//            int durationMins = durationMinutes % 60;

            String dateString = String.format("%04d-%02d-%02d", year, month, day);
            String sleepTimeString = String.format("%02d:%02d", sleepHour, sleepMinute);
            String awakeTimeString = String.format("%02d:%02d", awakeHour, awakeMinute);

            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("date", sdf.format(date));
            values.put("sleep_time", sleepTimeString);
            values.put("awake_time", awakeTimeString);
            values.put("email", email);
            long res = db.insert("sleepTracker", null, values);
            if(res != -1){
                Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Update failed", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView deleteBtn = findViewById(R.id.delete_button);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                String selection1 = "email = ?";
                String selection2 = "date = ?";
                String[] selectionArgs = { email, sdf.format(date) };

                long res = db.delete("sleepTracker", selection1 + " = ? AND " + selection2 + " = ?", selectionArgs);
                if(res != -1){
                    Toast.makeText(getApplicationContext(), "Delete successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}