package com.example.healthcareapp.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.healthcareapp.R;
import com.example.healthcareapp.activities.AddHospitalActivity;

public class HospitalDashboardActivity extends AppCompatActivity {

    CardView cardAddHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_dashboard);

        cardAddHospital = findViewById(R.id.cardAddHospital);
        cardAddHospital.setOnClickListener(v ->
                startActivity(new Intent(this, AddHospitalActivity.class))
        );
    }
}
