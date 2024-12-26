package com.fiek.medicalapplication_paisjemobile;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import android.widget.Button;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;


public class ProfileActivity extends AppCompatActivity {

    private TextInputLayout etNameLayout, etSurnameLayout, etAgeLayout, etEmailLayout, etPhoneLayout, etAddressLayout;
    private TextInputEditText etName, etSurname, etAge, etEmail, etPhone, etAddress;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        etNameLayout = findViewById(R.id.et_name_layout);
        etSurnameLayout = findViewById(R.id.et_surname_layout);
        etAgeLayout = findViewById(R.id.et_age_layout);
        etEmailLayout = findViewById(R.id.et_email_layout);
        etPhoneLayout = findViewById(R.id.et_phone_layout);
        etAddressLayout = findViewById(R.id.et_address_layout);

        etName = findViewById(R.id.et_name);
        etSurname = findViewById(R.id.et_surname);
        etAge = findViewById(R.id.et_age);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_address);

        btnLogout = findViewById(R.id.btn_logout);

        // Logout button click listener
        btnLogout.setOnClickListener(v -> {
            if (isValidInput()) {
                // Perform logout actions here (e.g., navigate to login screen)
                // Example: startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });
    }

    // Validate input fields
    private boolean isValidInput() {
        boolean isValid = true;

        // Name validation
        if (TextUtils.isEmpty(etName.getText())) {
            etNameLayout.setError("Emri është i detyrueshëm");
            isValid = false;
        } else {
            etNameLayout.setError(null);
        }

        // Surname validation
        if (TextUtils.isEmpty(etSurname.getText())) {
            etSurnameLayout.setError("Mbiemri është i detyrueshëm");
            isValid = false;
        } else {
            etSurnameLayout.setError(null);
        }

        // Age validation
        if (TextUtils.isEmpty(etAge.getText())) {
            etAgeLayout.setError("Mosha është e detyrueshme");
            isValid = false;
        } else {
            etAgeLayout.setError(null);
        }

        // Email validation
        if (TextUtils.isEmpty(etEmail.getText()) || !Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches()) {
            etEmailLayout.setError("Email-i është i detyrueshëm dhe duhet të jetë i vlefshëm");
            isValid = false;
        } else {
            etEmailLayout.setError(null);
        }

        // Phone validation
        if (TextUtils.isEmpty(etPhone.getText())) {
            etPhoneLayout.setError("Numri i telefonit është i detyrueshëm");
            isValid = false;
        } else {
            etPhoneLayout.setError(null);
        }

        // Address validation
        if (TextUtils.isEmpty(etAddress.getText())) {
            etAddressLayout.setError("Adresa është e detyrueshme");
            isValid = false;
        } else {
            etAddressLayout.setError(null);
        }

        return isValid;
    }
}

