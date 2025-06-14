package com.example.healthcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcareapp.Dashboard.HospitalDashboardActivity;
import com.example.healthcareapp.Dashboard.PatientDashboardActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginBtn, gotoRegisterBtn;
    private FirebaseAuth auth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        // UI Components
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginBtn);
        gotoRegisterBtn = findViewById(R.id.gotoRegisterBtn);

        // Login Button
        loginBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            // Validate input
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Sign in
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String uid = auth.getCurrentUser().getUid();

                            userRef.child(uid).get().addOnCompleteListener(roleTask -> {
                                if (roleTask.isSuccessful() && roleTask.getResult().exists()) {
                                    DataSnapshot snapshot = roleTask.getResult();
                                    String role = snapshot.child("role").getValue(String.class);
                                    if ("Patient".equalsIgnoreCase(role)) {
                                        startActivity(new Intent(this, PatientDashboardActivity.class));
                                    } else if ("Hospital".equalsIgnoreCase(role)) {
                                        startActivity(new Intent(this, HospitalDashboardActivity.class));
                                    } else {
                                        Toast.makeText(this, "Unknown user role", Toast.LENGTH_SHORT).show();
                                    }

                                    finish();
                                } else {
                                    Toast.makeText(this, "Failed to fetch user role", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            String message = task.getException() != null ?
                                    task.getException().getMessage() : "Login failed";
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Register Button
        gotoRegisterBtn.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );
    }
}

