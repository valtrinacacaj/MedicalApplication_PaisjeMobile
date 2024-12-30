package com.fiek.medicalapplication_paisjemobile;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class HomeActivity extends AppCompatActivity {

    private String userEmail;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        DatabaseHelper dbHelper = new DatabaseHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", "");

        // Find views
        CardView cardUpcomingAppointment = findViewById(R.id.cardUpcomingAppointment);
        CardView cardTakeMedication = findViewById(R.id.cardTakeMedication);
        ImageView profileIcon = findViewById(R.id.profileIcon);

        // Set up listeners
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