package my.edu.utar.periodtracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Symptom extends AppCompatActivity {

    // Create an array of cramp images
    int[] crampImages = {R.drawable.stomach, R.drawable.migraine, R.drawable.cramp};
    int[] fluidImages = {R.drawable.egg_white, R.drawable.green, R.drawable.with_blood};
    int[] mentalImages = {R.drawable.insomnia, R.drawable.stress, R.drawable.angry};

    // Keep track of the selected cramp index
    private int selectedCrampIndex = -1;
    private int selectedFluidIndex = -1;
    private int selectedMentalIndex = -1;

    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom);

        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        currentDate = intent.getStringExtra("selectedDay");

        LinearLayout crampLayout = findViewById(R.id.cramp_layout);
        LinearLayout fluidLayout = findViewById(R.id.fluid_layout);
        LinearLayout mentalLayout = findViewById(R.id.mental_layout);

        // Create the ImageButtons for the cramp symptom
        for (int i = 0; i < crampImages.length; i++) {
            ImageButton crampButton = createSymptomButton(crampImages[i], crampLayout, i);
            crampLayout.addView(crampButton);
        }

        // Create the ImageButtons for the fluid symptom
        for (int i = 0; i < fluidImages.length; i++) {
            ImageButton fluidButton = createSymptomButton(fluidImages[i], fluidLayout, i);
            fluidLayout.addView(fluidButton);
        }

        // Create the ImageButtons for the mental symptom
        for (int i = 0; i < mentalImages.length; i++) {
            ImageButton mentalButton = createSymptomButton(mentalImages[i], mentalLayout, i);
            mentalLayout.addView(mentalButton);
        }

        Button saveButton = findViewById(R.id.save_button);
        // Declare arrays to keep track of selected indexes for each layout
        ArrayList<Integer> selectedCrampIndexes = new ArrayList<>();
        ArrayList<Integer> selectedFluidIndexes = new ArrayList<>();
        ArrayList<Integer> selectedMentalIndexes = new ArrayList<>();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the selected indexes for each layout to the database
                DatabaseHelper dbHelper = new DatabaseHelper(Symptom.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                // Save selected cramp indexes
                for (int i = 0; i < selectedCrampIndexes.size(); i++) {
                    values.clear();
                    values.put(SymptomContract.SymptomEntry.COLUMN_NAME_IMAGE, crampImages[selectedCrampIndexes.get(i)]);
                    values.put(SymptomContract.SymptomEntry.COLUMN_NAME_DATE, currentDate);
                    db.insert(SymptomContract.SymptomEntry.TABLE_SYMPTOMS, null, values);
                }

                // Save selected fluid indexes
                for (int i = 0; i < selectedFluidIndexes.size(); i++) {
                    values.clear();
                    values.put(SymptomContract.SymptomEntry.COLUMN_NAME_IMAGE, fluidImages[selectedFluidIndexes.get(i)]);
                    values.put(SymptomContract.SymptomEntry.COLUMN_NAME_DATE, currentDate);
                    db.insert(SymptomContract.SymptomEntry.TABLE_SYMPTOMS, null, values);
                }

                // Save selected mental indexes
                for (int i = 0; i < selectedMentalIndexes.size(); i++) {
                    values.clear();
                    values.put(SymptomContract.SymptomEntry.COLUMN_NAME_IMAGE, mentalImages[selectedMentalIndexes.get(i)]);
                    values.put(SymptomContract.SymptomEntry.COLUMN_NAME_DATE, currentDate);
                    db.insert(SymptomContract.SymptomEntry.TABLE_SYMPTOMS, null, values);
                }

                db.close();

                // Show a toast message to indicate successful storage
                Toast.makeText(Symptom.this, "Selected images and date stored in database.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Symptom.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
    }

    private ImageButton createSymptomButton(int imageResId, LinearLayout layout, int index) {
        Intent intent = getIntent();
        currentDate = intent.getStringExtra("selectedDay");

        LinearLayout crampLayout = findViewById(R.id.cramp_layout);
        LinearLayout fluidLayout = findViewById(R.id.fluid_layout);
        LinearLayout mentalLayout = findViewById(R.id.mental_layout);

        // Create the ImageButton and set its properties
        ImageButton button = new ImageButton(this);
        button.setImageResource(imageResId);
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        button.setPadding(20, 20, 20, 20);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        button.setLayoutParams(params);

        // Declare arrays to keep track of selected indexes for each layout
        ArrayList<Integer> selectedCrampIndexes = new ArrayList<>();
        ArrayList<Integer> selectedFluidIndexes = new ArrayList<>();
        ArrayList<Integer> selectedMentalIndexes = new ArrayList<>();

        // Set the OnClickListener for the ImageButton
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the LinearLayout and selected indexes based on the given layout
                LinearLayout selectedLayout = null;
                ArrayList<Integer> selectedIndexes = null;
                if (layout == crampLayout) {
                    selectedLayout = crampLayout;
                    selectedIndexes = selectedCrampIndexes;
                } else if (layout == fluidLayout) {
                    selectedLayout = fluidLayout;
                    selectedIndexes = selectedFluidIndexes;
                } else if (layout == mentalLayout) {
                    selectedLayout = mentalLayout;
                    selectedIndexes = selectedMentalIndexes;
                }

                // Check if the button is already selected
                if (button.isSelected()) {
                    // Unselect the button and remove its index from the selected indexes list
                    button.setSelected(false);
                    button.setBackgroundColor(Color.TRANSPARENT);
                    selectedIndexes.remove((Integer) index);
                } else {
                    // Select the button and add its index to the selected indexes list
                    button.setSelected(true);
                    button.setBackgroundResource(R.drawable.border);
                    selectedIndexes.add(index);
                }
            }
        });

        return button;
    }

}