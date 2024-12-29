package com.fiek.medicalapplication_paisjemobile;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        // Inicializimi i DatabaseHelper dhe listës së emërimeve
        dbHelper = new DatabaseHelper(this);
        appointments = new ArrayList<>();

        // Inicializimi i RecyclerView dhe adapterit
        RecyclerView recyclerView = findViewById(R.id.appointmentListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AppointmentAdapter(this, appointments);
        recyclerView.setAdapter(adapter);

        // Rifreskimi i të dhënave nga database
        refreshAppointments();

        // Vendosja e listener-it për FloatingActionButton
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(UpcomingAppointmentActivity.this, UploadActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAppointments();
    }

    private void refreshAppointments() {
        appointments.clear();

        Cursor cursor = dbHelper.getAllAppointments();
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        String title = cursor.getString(cursor.getColumnIndexOrThrow("appointment_name"));
                        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                        appointments.add(new Appointment(title, description, date));
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error reading from database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
