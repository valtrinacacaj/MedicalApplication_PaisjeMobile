package com.fiek.medicalapplication_paisjemobile;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailInput;
    private Button requestPasswordResetButton;
    private TextView backToSignInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailInput = findViewById(R.id.emailInput);
        requestPasswordResetButton = findViewById(R.id.requestPasswordResetButton);
        backToSignInText = findViewById(R.id.backToSignInText);

        // Butoni për të dërguar kërkesën
        requestPasswordResetButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            if (email.isEmpty()) {
                emailInput.setError("Please enter your email");
                return;
            }
            // Dërgo kërkesën për resetimin e fjalëkalimit
            sendPasswordResetRequest(email);
        });

        // Link për t'u kthyer në LogInActivity
        backToSignInText.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void sendPasswordResetRequest(String email) {
        final String username = "luragashi4@gmail.com"; // Adresa juaj e emailit
        final String password = ""; // Fjalëkalimi i emailit tuaj

        // Parametrat e serverit SMTP të Gmail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Krijimi i emailit dhe dërgimi
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Password Reset Request");
            message.setText("Click the following link to reset your password: https://your-reset-link.com");

            // Dërgo emailin
            Transport.send(message);

            // Shfaq mesazh sukses
            Toast.makeText(this, "Password reset link sent to " + email, Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to send reset email.", Toast.LENGTH_SHORT).show();
        }
    }
}
