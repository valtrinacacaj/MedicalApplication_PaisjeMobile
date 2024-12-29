package com.fiek.medicalapplication_paisjemobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TakeMedicationActivity extends AppCompatActivity {
    private ArrayList<Medication> medications;
    private MedicationAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take);

        dbHelper = new DatabaseHelper(this);
        medications = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.medicationListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedicationAdapter(this, medications);
        recyclerView.setAdapter(adapter);

        // Rifreskimi i të dhënave nga database
        refreshMedication();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(TakeMedicationActivity.this, UploadMedication.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshMedication();
    }

    private void refreshMedication() {
        medications.clear();

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId == -1) {
            Toast.makeText(this, "Error loading medications", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = dbHelper.getMedicationsFromUsers(userId);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("medication_name"));
                    String dosage = cursor.getString(cursor.getColumnIndexOrThrow("dosage"));
                    String scheduler = cursor.getString(cursor.getColumnIndexOrThrow("scheduler"));
                    medications.add(new Medication(name, dosage, scheduler));
                }
            } finally {
                cursor.close();
            }
        }

        adapter.notifyDataSetChanged();

        if (medications.isEmpty()) {
            Toast.makeText(this, "No upcoming medications", Toast.LENGTH_SHORT).show();
        }
    }
}
