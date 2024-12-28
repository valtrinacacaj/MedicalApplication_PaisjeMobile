package com.fiek.medicalapplication_paisjemobile;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText etName, etSurname, etAge, etEmail;
    private Button btnLogout, btnEdit, btnSave;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Inicializimi i view-ve
        etName = findViewById(R.id.et_name);
        etSurname = findViewById(R.id.et_surname);
        etAge = findViewById(R.id.et_age);
        etEmail = findViewById(R.id.et_email);

        btnLogout = findViewById(R.id.btn_logout);
        btnEdit = findViewById(R.id.btn_edit);
        btnSave = findViewById(R.id.btn_save);

        // Inicializimi i DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Marrja e email-it të përdoruesit që është loguar
        String userEmail = getIntent().getStringExtra("USER_EMAIL");

        // Thirrja e funksionit për të marrë të dhënat e përdoruesit nga databaza
        fetchUserData(userEmail);

        // Logout Button
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        });

        // Edit Button (Aktivizon fushat për modifikim)
        btnEdit.setOnClickListener(v -> enableEditing(true));

        // Save Button (Ruaj të dhënat e modifikuara)
        btnSave.setOnClickListener(v -> {
            saveUserData();
            enableEditing(false);
        });
    }

    // Metodë për të marrë të dhënat e përdoruesit nga baza e të dhënave
    private void fetchUserData(String email) {
        Cursor cursor = databaseHelper.getFullUserData(email);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String surname = cursor.getString(cursor.getColumnIndexOrThrow("surname"));
            String age = cursor.getString(cursor.getColumnIndexOrThrow("age"));

            etName.setText(name);
            etSurname.setText(surname);
            etAge.setText(age);
            etEmail.setText(email);
        } else {
            Toast.makeText(this, "No user data found!", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    // Metodë për të aktivizuar ose deaktivizuar modifikimin e të dhënave
    private void enableEditing(boolean enable) {
        etName.setEnabled(enable);
        etSurname.setEnabled(enable);
        etAge.setEnabled(enable);
        etEmail.setEnabled(false); // Email është i pa-redaktueshëm
        btnEdit.setVisibility(enable ? View.GONE : View.VISIBLE);
        btnSave.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    // Metodë për të ruajtur të dhënat e modifikuara
    private void saveUserData() {
        String name = etName.getText().toString().trim();
        String surname = etSurname.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (name.isEmpty() || surname.isEmpty() || age.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Përditëso të dhënat në bazën e të dhënave
        boolean isUpdated = databaseHelper.updateUserData(name, surname, age, email);
        if (isUpdated) {
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }
}
