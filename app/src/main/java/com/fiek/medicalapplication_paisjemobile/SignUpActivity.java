package com.fiek.medicalapplication_paisjemobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.fiek.medicalapplication_paisjemobile.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.signupName.getText().toString().trim();
                String surname = binding.signupSurname.getText().toString().trim();
                String age = binding.signupAge.getText().toString().trim();
                String email = binding.signupEmail.getText().toString().trim();
                String password = binding.signupPassword.getText().toString();
                String repeatPassword = binding.signupRepeatpassword.getText().toString();

                // Validate input
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) || TextUtils.isEmpty(age) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repeatPassword)) {
                    showAlert("All fields are mandatory.");
                    return;
                }

                if (!age.matches("\\d+") || Integer.parseInt(age) <= 0) {
                    showAlert("Please enter a valid age.");
                    return;
                }

                if (!password.equals(repeatPassword)) {
                    showAlert("Passwords do not match.");
                    return;
                }

                String passwordValidationMessage = getPasswordValidationMessage(password);
                if (passwordValidationMessage != null) {
                    showAlert(passwordValidationMessage);
                    return;
                }

                if (databaseHelper.checkEmail(email)) {
                    showAlert("User already exists! Please login.");
                    return;
                }

                boolean insert = databaseHelper.insertData(name, surname, age, email, password);
                if (insert) {
                    Toast.makeText(SignUpActivity.this, "Signup Successful! Please log in.", Toast.LENGTH_SHORT).show();

                    // Pas regjistrimit, dërgo përdoruesin në LogInActivity
                    Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                    startActivity(intent);
                    finish(); // Mbyll SignUpActivity
                } else {
                    showAlert("Signup Failed! Please try again.");
                }
            }
        });

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
                finish(); // Close SignUpActivity
            }
        });
    }

    private String getPasswordValidationMessage(String password) {
        if (password.length() < 8) {
            return "Password must be at least 8 characters long.";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Password must contain at least one capital letter.";
        }
        if (!password.matches(".*\\d.*")) {
            return "Password must contain at least one number.";
        }
        return null;
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }
}
