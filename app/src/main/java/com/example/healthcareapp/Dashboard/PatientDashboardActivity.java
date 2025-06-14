package com.example.healthcareapp.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.healthcareapp.R;
import com.example.healthcareapp.activities.HospitalListActivity;

public class PatientDashboardActivity extends AppCompatActivity {
    CardView cardHospitals, cardAppointments, cardPrescriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
        Toast.makeText(this, "Patient Dashboard Opened", Toast.LENGTH_SHORT).show();
        cardHospitals = findViewById(R.id.cardHospitals);
        cardAppointments = findViewById(R.id.cardAppointments);
        cardPrescriptions = findViewById(R.id.cardPrescriptions);

        cardHospitals.setOnClickListener(v ->
                startActivity(new Intent(this, HospitalListActivity.class)));


    }
}
