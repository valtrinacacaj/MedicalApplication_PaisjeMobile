package com.fiek.medicalapplication_paisjemobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Appointment> appointments;

    public AppointmentAdapter(Context context, ArrayList<Appointment> appointments) {
        this.context = context;
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.appointment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.titleTextView.setText(appointment.getTitle());
        holder.descriptionTextView.setText(appointment.getDescription());
        holder.dateTextView.setText(appointment.getDate());
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView, dateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.appointment_title);
            descriptionTextView = itemView.findViewById(R.id.appointment_description);
            dateTextView = itemView.findViewById(R.id.appointment_date);
        }
    }
}
