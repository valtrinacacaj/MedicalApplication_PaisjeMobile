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
public class UpcomingAppointmentActivity extends AppCompatActivity {

    private ArrayList<Appointment> appointments;
    private AppointmentAdapter adapter;
    private DatabaseHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        dbHelper = new DatabaseHelper(this);
        appointments = new ArrayList<>();

        // Merrni userId nga SharedPreferences ose një burim tjetër
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        if (userId == -1) {
            Toast.makeText(this, "Error loading appointments", Toast.LENGTH_SHORT).show();
            return;
        }

        RecyclerView recyclerView = findViewById(R.id.appointmentListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AppointmentAdapter(this, appointments, dbHelper);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> startActivity(new Intent(UpcomingAppointmentActivity.this, UploadActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAppointments();
    }

    private void refreshAppointments() {
        appointments.clear();
        Cursor cursor = dbHelper.getAppointmentsForUser(userId);

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow("appointment_name"));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));

                    appointments.add(new Appointment(id, title, description, date));
                }
            } finally {
                cursor.close();
            }
        }

        adapter.notifyDataSetChanged();

        if (appointments.isEmpty()) {
            Toast.makeText(this, "No upcoming appointments", Toast.LENGTH_SHORT).show();
        }
    }
}