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
                String ageString = binding.signupAge.getText().toString().trim();
                String email = binding.signupEmail.getText().toString().trim();
                String password = binding.signupPassword.getText().toString();
                String repeatPassword = binding.signupRepeatpassword.getText().toString();

                // Kontrollo nëse emri dhe mbiemri janë të vlefshme
                if (!isValidNameOrSurname(name) || !isValidNameOrSurname(surname)) {
                    showAlert("Name and surname should only contain letters.");
                    return;
                }

                // Kontrollo nëse emaili është i vlefshëm
                if (!isValidEmail(email)) {
                    showAlert("Please enter a valid email address.");
                    return;
                }
                // Kontrollo nëse mosha është e vlefshme (duhet të jetë mbi 14 vjeç)
                int age = -1; // Kjo do të përdoret për të ruajtur moshën si integer
                if (!isValidAge(ageString)) {
                    showAlert("You must be over 14 years old to register.");
                    return;
                } else {
                    age = Integer.parseInt(ageString); // Konvertoni moshën në int
                }
                // Validate input
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) || TextUtils.isEmpty(ageString) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repeatPassword)) {
                    showAlert("All fields are mandatory.");
                    return;
                }

                if (!ageString.matches("\\d+") || Integer.parseInt(ageString) <= 0) {
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

                boolean insert = databaseHelper.insertData(name, surname, age, email, password); // Kalo `age` si int
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

    // Shtimi i metodës për kontrollimin e formatit të emailit
    public boolean isValidEmail(String email) {
        // Pattern i rregullt për emailin
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailPattern); // Kthe true nëse emaili përputhet me pattern-in
    }
    // Metoda për të kontrolluar emrin dhe mbiemrin, vetëm shkronja të lejueshme
    public boolean isValidNameOrSurname(String input) {
        return input.matches("[A-Za-z]+"); // Emri dhe mbiemri mund të përmbajnë vetëm shkronja
    }

    // Metoda për të kontrolluar moshën (numra pozitiv dhe mbi 14 vjeç)
    public boolean isValidAge(String ageString) {
        if (!ageString.matches("\\d+")) {
            return false; // Mosha duhet të jetë numër
        }
        int age = Integer.parseInt(ageString);
        return age > 14 && age <= 117; // Mosha duhet të jetë midis 15 dhe 117
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
