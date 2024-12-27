package com.fiek.medicalapplication_paisjemobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpcomingAppointmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        // Gjej FloatingActionButton sipas ID-së
        FloatingActionButton fab = findViewById(R.id.fab);

        // Kontrollo nëse fab është i inicializuar
        if (fab != null) {
            // Vendos OnClickListener për FloatingActionButton
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Hap aktivitetin UploadActivity
                    Intent intent = new Intent(UpcomingAppointmentActivity.this, UploadActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            // Shfaq një log për gabim (opsionale)
            android.util.Log.e("UpcomingActivity", "FAB nuk u gjet!");
        }
    }
}
