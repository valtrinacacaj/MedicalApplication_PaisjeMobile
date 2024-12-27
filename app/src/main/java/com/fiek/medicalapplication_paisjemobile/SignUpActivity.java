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

                // Kontrollo nëse fushat janë të zbrazëta
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) || TextUtils.isEmpty(age) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repeatPassword)) {
                    showAlert("All fields are mandatory.");
                    return;
                }

                // Kontrollo nëse mosha është e vlefshme
                if (!age.matches("\\d+") || Integer.parseInt(age) <= 0) {
                    showAlert("Please enter a valid age.");
                    return;
                }

                // Kontrollo nëse fjalëkalimet përputhen
                if (!password.equals(repeatPassword)) {
                    showAlert("Passwords do not match.");
                    return;
                }

                // Validimi i fjalëkalimit
                String passwordValidationMessage = getPasswordValidationMessage(password);
                if (passwordValidationMessage != null) {
                    showAlert(passwordValidationMessage);
                    return;
                }

                // Kontrollo nëse email-i ekziston
                if (databaseHelper.checkEmail(email)) {
                    showAlert("User already exists! Please login.");
                    return;
                }

                // Fut të dhënat në bazën e të dhënave
                boolean insert = databaseHelper.insertData(name, surname, age, email, password);
                if (insert) {
                    Toast.makeText(SignUpActivity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                    startActivity(intent);
                    finish(); // Mbyll aktivitetin aktual
                } else {
                    showAlert("Signup Failed! Please try again.");
                }
            }
        });

        // Kalimi në ekranin e Login
        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
                finish(); // Mbyll aktivitetin aktual
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
        return null; // Fjalëkalimi është i vlefshëm
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
