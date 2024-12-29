package com.fiek.medicalapplication_paisjemobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Medication> medications;

    public MedicationAdapter(Context context, ArrayList<Medication> medications) {
        this.context = context;
        this.medications = medications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.medication_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medication medication = medications.get(position);
        holder.nameTextView.setText(medication.getName());
        holder.dosageTextView.setText(medication.getDosage());
        holder.schedulerTextView.setText(medication.getScheduler());
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, dosageTextView, schedulerTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.uploadMedicationName);
            dosageTextView = itemView.findViewById(R.id.uploadDosage);
            schedulerTextView = itemView.findViewById(R.id.uploadScheduler);
        }
    }
}
