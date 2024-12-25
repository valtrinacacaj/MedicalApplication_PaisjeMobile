package com.fiek.medicalapplication_paisjemobile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

        try {

            databaseHelper = new DatabaseHelper(this);

            binding.loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = binding.loginEmail.getText().toString();
                    String password = binding.loginPassword.getText().toString();

                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(LogInActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);
                        if (checkCredentials) {
                            Toast.makeText(LogInActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                            // Kaluar te HomeActivity
                            Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LogInActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            Log.e("LogInActivity", "Error initializing the activity: " + e.getMessage());
            Toast.makeText(this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
