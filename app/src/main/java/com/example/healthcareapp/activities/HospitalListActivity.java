package com.example.healthcareapp.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcareapp.R;
import com.example.healthcareapp.adapters.HospitalAdapter;
import com.example.healthcareapp.models.Hospital;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class HospitalListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Hospital> hospitalList;
    private HospitalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        recyclerView = findViewById(R.id.recyclerViewHospitals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hospitalList = new ArrayList<>();
        adapter = new HospitalAdapter(this, hospitalList);
        recyclerView.setAdapter(adapter);

        loadHospitalsFromFirebase();
    }

    private void loadHospitalsFromFirebase() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Hospitals");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hospitalList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Hospital hospital = ds.getValue(Hospital.class);
                    if (hospital != null) {
                        hospitalList.add(hospital);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
