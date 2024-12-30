package com.fiek.medicalapplication_paisjemobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.fiek.medicalapplication_paisjemobile.databinding.ActivityLogInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    ActivityLogInBinding binding;
    DatabaseHelper databaseHelper;

    private FirebaseAuth mAuth;

    TextView forgotPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Instanco DatabaseHelper
        databaseHelper = new DatabaseHelper(this);
        mAuth = FirebaseAuth.getInstance();

        forgotPasswordText = findViewById(R.id.forgotPasswordText);
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.activity_forgot_password, null);
                EditText emailInput = dialogView.findViewById(R.id.emailInput);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.requestPasswordResetButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userEmail = emailInput.getText().toString();

                        if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                            Toast.makeText(LogInActivity.this, "Enter your registered email id", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LogInActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(LogInActivity.this, "Unable to send, failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                });
                dialog.show();
            }
        });

        // Klikimi për Login
        binding.loginButton.setOnClickListener(v -> {
            String email = binding.loginEmail.getText().toString().trim();
            String password = binding.loginPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LogInActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                if (databaseHelper.checkEmailPassword(email, password)) {
                    // Merr userId dhe ruaje në SharedPreferences
                    int userId = databaseHelper.getUserIdFromEmail(email);
                    SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("userId", userId);
                    editor.putString("email", email);
                    editor.apply();

                    Toast.makeText(LogInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogInActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LogInActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Redirekto te SignUpActivity nëse përdoruesi nuk ka llogari
        binding.signupRedirectText.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
