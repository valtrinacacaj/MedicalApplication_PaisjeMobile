package com.fiek.medicalapplication_paisjemobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class UploadMedication extends AppCompatActivity {

    private Button saveButton;
    private EditText uploadMedicationName, uploadDosage;
    private Spinner uploadSchedule; // Declare the Spinner here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadmedication);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Initialize UI components
        uploadMedicationName = findViewById(R.id.uploadMedicationName);
        uploadDosage = findViewById(R.id.uploadDosage);
        saveButton = findViewById(R.id.saveButton);
        uploadSchedule = findViewById(R.id.uploadScheduler);  // Initialize the Spinner here

        saveButton.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
            int userId = sharedPreferences.getInt("userId", -1);

            if (userId == -1) {
                Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
                return;
            }

            String medicationName = uploadMedicationName.getText().toString().trim();
            String dosage = uploadDosage.getText().toString().trim();
            String schedule = uploadSchedule.getSelectedItem().toString();

            if (medicationName.isEmpty() || dosage.isEmpty() || schedule.isEmpty()) {
                Toast.makeText(UploadMedication.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isInserted = dbHelper.insertMedication(medicationName, dosage, schedule, userId);
            if (isInserted) {
                Toast.makeText(UploadMedication.this, "Medication saved successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(UploadMedication.this, "Failed to save medication!", Toast.LENGTH_SHORT).show();
            }
        });

        // Spinner item selection listener for the schedule
        uploadSchedule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedSchedule = parentView.getItemAtPosition(position).toString();
                // Do something with the selected schedule
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle case where nothing is selected
            }
        });
    }
}
