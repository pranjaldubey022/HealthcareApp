package com.example.healthcareapp.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthcareapp.R;
import com.example.healthcareapp.models.Doctor;
import com.example.healthcareapp.models.Hospital;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.*;

public class AddHospitalActivity extends AppCompatActivity {

    private TextInputEditText etHospitalName, etHospitalAddress, etFacilities, etDoctorName, etDoctorSpecialization;
    private MaterialButton btnSubmitHospital;
    private DatabaseReference hospitalsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hospital);

        etHospitalName = findViewById(R.id.etHospitalName);
        etHospitalAddress = findViewById(R.id.etHospitalAddress);
        etFacilities = findViewById(R.id.etFacilities);
        etDoctorName = findViewById(R.id.etDoctorName);
        etDoctorSpecialization = findViewById(R.id.etDoctorSpecialization);
        btnSubmitHospital = findViewById(R.id.btnSubmitHospital);

        hospitalsRef = FirebaseDatabase.getInstance().getReference("Hospitals");

        btnSubmitHospital.setOnClickListener(v -> submitHospitalData());
    }

    private void submitHospitalData() {
        String name = etHospitalName.getText().toString().trim();
        String address = etHospitalAddress.getText().toString().trim();
        String facilitiesText = etFacilities.getText().toString().trim();
        String doctorName = etDoctorName.getText().toString().trim();
        String doctorSpecialization = etDoctorSpecialization.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address) ||
                TextUtils.isEmpty(facilitiesText) || TextUtils.isEmpty(doctorName) ||
                TextUtils.isEmpty(doctorSpecialization)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert comma-separated facilities to list
        List<String> facilitiesList = Arrays.asList(facilitiesText.split("\\s*,\\s*"));

        // Create doctor and hospital objects
        Doctor doctor = new Doctor(doctorName, doctorSpecialization);
        List<Doctor> doctorsList = new ArrayList<>();
        doctorsList.add(doctor);

        Hospital hospital = new Hospital(name, address);
        hospital.setFacilities(facilitiesList);
        hospital.setDoctors(doctorsList);

        // Push hospital to Firebase
        String hospitalId = hospitalsRef.push().getKey();
        if (hospitalId != null) {
            hospitalsRef.child(hospitalId).setValue(hospital)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Hospital added successfully", Toast.LENGTH_SHORT).show();
                            clearFields();
                        } else {
                            Toast.makeText(this, "Failed to add hospital", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void clearFields() {
        etHospitalName.setText("");
        etHospitalAddress.setText("");
        etFacilities.setText("");
        etDoctorName.setText("");
        etDoctorSpecialization.setText("");
    }
}
