package my.edu.utar.periodtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    private TextView textViewDateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        ImageView backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageView deleteButton = findViewById(R.id.imageViewDelete);
        ImageView doneButton = findViewById(R.id.imageViewDone);

        // Set the click listener for all ImageViews
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NoteActivity.this, "Done Saved", Toast.LENGTH_SHORT).show();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NoteActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
            }
        });

        // Find the TextView by its id
        textViewDateTime = findViewById(R.id.textViewDateTime);

        // Get the current date and time
        Intent intent = getIntent();
        String date = intent.getStringExtra("selectedDay");

        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Set the current date and time to the TextView
        //textViewDateTime.setText("Date and Time: " + currentDateTime);
        textViewDateTime.setText("Date : " + date);
    }
}