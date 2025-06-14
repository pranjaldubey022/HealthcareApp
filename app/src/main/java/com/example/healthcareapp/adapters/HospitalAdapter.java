package com.example.healthcareapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcareapp.R;
import com.example.healthcareapp.models.Hospital;
import com.example.healthcareapp.activities.HospitalDetailsActivity;

import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {

    private Context context;
    private List<Hospital> hospitalList;

    public HospitalAdapter(Context context, List<Hospital> hospitalList) {
        this.context = context;
        this.hospitalList = hospitalList;
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hospital_item, parent, false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        Hospital hospital = hospitalList.get(position);

        holder.name.setText(hospital.getName());
        holder.address.setText(hospital.getAddress());

        // Add click listener to open HospitalDetailsActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HospitalDetailsActivity.class);
            intent.putExtra("hospitalId", hospital.getId());  // Pass ID to details activity
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    public static class HospitalViewHolder extends RecyclerView.ViewHolder {
        TextView name, address;

        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hospitalName);
            address = itemView.findViewById(R.id.hospitalAddress);
        }
    }
}
