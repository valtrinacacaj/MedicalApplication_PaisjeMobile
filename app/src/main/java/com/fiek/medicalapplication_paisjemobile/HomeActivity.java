package com.fiek.medicalapplication_paisjemobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", "");


        androidx.cardview.widget.CardView cardUpcomingAppointment = findViewById(R.id.cardUpcomingAppointment);
        androidx.cardview.widget.CardView cardTakeMedication = findViewById(R.id.cardTakeMedication);
        ImageView profileIcon = findViewById(R.id.profileIcon);


        cardUpcomingAppointment.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, UpcomingAppointmentActivity.class);
            startActivity(intent);
        });

        cardTakeMedication.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, TakeMedicationActivity.class);
            startActivity(intent);
        });


        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.putExtra("USER_EMAIL", userEmail);
            startActivity(intent);
        });
    }
}
