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

        // Koha e animacionit (p.sh., 3 sekonda)
        int splashTime = 3000;

        // Pas kalimit të kohës, kalon në HomeActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Kalo në HomeActivity
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Mbyll SplashActivity pasi kalon në HomeActivity
            }
        }, splashTime);
    }
}
