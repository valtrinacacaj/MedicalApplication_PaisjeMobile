package com.fiek.medicalapplication_paisjemobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Merr email-in që vjen nga LogInActivity
        String userEmail = getIntent().getStringExtra("USER_EMAIL");

        // Koha e animacionit (3 sekonda)
        int splashTime = 3000;

        // Pas kalimit të kohës, kalon në HomeActivity
        new Handler().postDelayed(() -> {
            // Kalo në HomeActivity duke kaluar email-in
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            intent.putExtra("USER_EMAIL", userEmail); // Dërgo email-in te HomeActivity
            startActivity(intent);
            finish(); // Mbyll SplashActivity pasi kalon në HomeActivity
        }, splashTime);
    }
}
