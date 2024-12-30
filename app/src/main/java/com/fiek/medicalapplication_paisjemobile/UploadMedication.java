package com.fiek.medicalapplication_paisjemobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UploadMedication extends AppCompatActivity {
    private Button saveButton;
    private EditText uploadMedicationName, uploadDosage,uploadScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadmedication);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        uploadMedicationName = findViewById(R.id.uploadMedicationName); // Emërtimi ekzistues
        uploadDosage = findViewById(R.id.uploadDosage); // Përdoresh për doza
        uploadScheduler = findViewById(R.id.uploadScheduler); // ID e re për detaje
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
            int userId = sharedPreferences.getInt("userId", -1);

            if (userId == -1) {
                Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
                return;
            }

            String medicationName = uploadMedicationName.getText().toString().trim();
            String dosage = uploadDosage.getText().toString().trim();
            String scheduler = uploadScheduler.getText().toString().trim();

            if (medicationName.isEmpty() || dosage.isEmpty() || scheduler.isEmpty()) {
                Toast.makeText(UploadMedication.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isInserted = dbHelper.insertMedication(medicationName, dosage, scheduler, userId);
            if (isInserted) {
                Toast.makeText(UploadMedication.this, "Medication saved successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(UploadMedication.this, "Failed to save medication!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
