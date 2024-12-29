package com.fiek.medicalapplication_paisjemobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadActivity extends AppCompatActivity {
    private Button saveButton;
    private EditText uploadAppointment, uploadDesc;
    private DatePicker uploadDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        uploadAppointment = findViewById(R.id.uploadAppointment);
        uploadDesc = findViewById(R.id.uploadDesc);
        uploadDate = findViewById(R.id.uploadDate);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(view -> {
            String appointmentName = uploadAppointment.getText().toString().trim();
            String description = uploadDesc.getText().toString().trim();

            int day = uploadDate.getDayOfMonth();
            int month = uploadDate.getMonth();
            int year = uploadDate.getYear();
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(calendar.getTime());

            if (appointmentName.isEmpty() || description.isEmpty()) {
                Toast.makeText(UploadActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isInserted = dbHelper.insertAppointment(appointmentName, description, date);
            if (isInserted) {
                Toast.makeText(UploadActivity.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(UploadActivity.this, "Failed to save data!", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(UploadActivity.this, UpcomingAppointmentActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
