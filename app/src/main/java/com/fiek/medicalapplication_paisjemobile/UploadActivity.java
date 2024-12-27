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
    // Deklarimi i komponentëve
    private Button saveButton;
    private EditText uploadAppointment, uploadDesc;
    private DatePicker uploadDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        // Inicializimi i komponentëve
        uploadAppointment = findViewById(R.id.uploadAppointment);
        uploadDesc = findViewById(R.id.uploadDesc);
        uploadDate = findViewById(R.id.uploadDate);
        saveButton = findViewById(R.id.saveButton);

        // Vendos listener për saveButton
        saveButton.setOnClickListener(view -> {
            // Merr të dhënat nga fushat e EditText
            String appointmentName = uploadAppointment.getText().toString().trim();
            String description = uploadDesc.getText().toString().trim();

            // Merr datën nga DatePicker dhe e formaton
            int day = uploadDate.getDayOfMonth();
            int month = uploadDate.getMonth();
            int year = uploadDate.getYear();
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(calendar.getTime());

            // Validimi bazik
            if (appointmentName.isEmpty() || description.isEmpty() || date.isEmpty()) {
                Toast.makeText(UploadActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tregoni mesazhin e suksesit (për testim)
            Toast.makeText(UploadActivity.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();

            // Kaloni në aktivitetin tjetër (UpcomingActivity)
            Intent intent = new Intent(UploadActivity.this, UpcomingAppointmentActivity.class);
            startActivity(intent);
            finish(); // Mbyllni aktivitetin aktual për të mos u kthyer përsëri
        });
    }
}
