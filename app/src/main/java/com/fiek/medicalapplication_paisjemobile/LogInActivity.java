package com.fiek.medicalapplication_paisjemobile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.fiek.medicalapplication_paisjemobile.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {

    ActivityLogInBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Instanco DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Klikimi për Login
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.loginEmail.getText().toString().trim();
                String password = binding.loginPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LogInActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Kontrollo nëse email dhe password janë të sakta
                    boolean isValid = databaseHelper.checkEmailPassword(email, password);
                    if (isValid) {
                        Toast.makeText(LogInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        // Kalo në HomeActivity
                        Intent intent = new Intent(LogInActivity.this,SplashActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LogInActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Redirekto te SignUpActivity nëse përdoruesi nuk ka llogari
        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}








