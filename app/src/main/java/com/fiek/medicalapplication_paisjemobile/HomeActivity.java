package com.fiek.medicalapplication_paisjemobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Linking the CardViews
        androidx.cardview.widget.CardView cardUpcomingAppointment = findViewById(R.id.cardUpcomingAppointment);
        androidx.cardview.widget.CardView cardTakeMedication = findViewById(R.id.cardTakeMedication);
        ImageView profileIcon = findViewById(R.id.profileIcon);

        // Navigate to Upcoming Appointment Activity
        cardUpcomingAppointment.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, UpcomingAppointmentActivity.class);
            startActivity(intent);
        });

        // Navigate to Take Medication Activity
        cardTakeMedication.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, TakeMedicationActivity.class);
            startActivity(intent);
        });

        // Navigate to Profile Activity
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }
}
