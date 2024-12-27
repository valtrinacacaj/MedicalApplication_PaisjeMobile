package com.fiek.medicalapplication_paisjemobile;

import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
                String name = binding.signupName.getText().toString();
                String surname = binding.signupSurname.getText().toString();
                String age = binding.signupAge.getText().toString();
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String repeatPassword = binding.signupRepeatpassword.getText().toString();

                if (name.equals("") || surname.equals("") || email.equals("") || password.equals("") || repeatPassword.equals("")) {
                    showAlert("All fields are mandatory");
                } else {
                    if (password.equals(repeatPassword)) {
                        String passwordValidationMessage = getPasswordValidationMessage(password);
                        if (passwordValidationMessage == null) {
                            Boolean checkUserEmail = databaseHelper.checkEmail(email);
                            if (!checkUserEmail) {
                                Boolean insert = databaseHelper.insertData(name, surname, age, email, password);
                                if (insert) {
                                    Toast.makeText(SignUpActivity.this, "Signup Successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                                    startActivity(intent);
                                } else {
                                    showAlert("Signup Failed!");
                                }
                            } else {
                                showAlert("User already exists! Please login");
                            }
                        } else {
                            showAlert(passwordValidationMessage);
                        }
                    } else {
                        showAlert("Passwords do not match!");
                    }
                }
            }
        });

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
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
        return null; // Password is valid
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