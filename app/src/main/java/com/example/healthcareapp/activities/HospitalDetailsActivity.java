package com.example.healthcareapp.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcareapp.R;
import com.example.healthcareapp.adapters.DoctorAdapter;
import com.example.healthcareapp.models.Doctor;
import com.example.healthcareapp.models.Hospital;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class HospitalDetailsActivity extends AppCompatActivity {

    private TextView hospitalName, hospitalAddress, hospitalFacilities;
    private RecyclerView recyclerViewDoctors;
    private List<Doctor> doctorList;
    private DoctorAdapter doctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);

        hospitalName = findViewById(R.id.hospitalName);
        hospitalAddress = findViewById(R.id.hospitalAddress);
        hospitalFacilities = findViewById(R.id.hospitalFacilities);
        recyclerViewDoctors = findViewById(R.id.recyclerViewDoctors);

        recyclerViewDoctors.setLayoutManager(new LinearLayoutManager(this));
        doctorList = new ArrayList<>();
        doctorAdapter = new DoctorAdapter(doctorList);
        recyclerViewDoctors.setAdapter(doctorAdapter);

        String hospitalId = getIntent().getStringExtra("hospitalId");
        if (hospitalId != null) {
            loadHospitalDetails(hospitalId);
            loadDoctors(hospitalId);
        }
    }

    private void loadHospitalDetails(String hospitalId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Hospitals").child(hospitalId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Hospital hospital = snapshot.getValue(Hospital.class);
                if (hospital != null) {
                    hospitalName.setText(hospital.getName());
                    hospitalAddress.setText(hospital.getAddress());
                    List<String> facilities = hospital.getFacilities();
                    if (facilities != null && !facilities.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        for (String facility : facilities) {
                            sb.append("• ").append(facility).append("\n");
                        }
                        hospitalFacilities.setText(sb.toString());
                    } else {
                        hospitalFacilities.setText("No facilities listed.");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadDoctors(String hospitalId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Hospitals")
                .child(hospitalId).child("doctors");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctorList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Doctor doctor = ds.getValue(Doctor.class);
                    if (doctor != null) doctorList.add(doctor);
                }
                doctorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
