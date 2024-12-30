package com.fiek.medicalapplication_paisjemobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    private Context context;
    private ArrayList<Appointment> appointments;
    private DatabaseHelper dbHelper;

    public AppointmentAdapter(Context context, ArrayList<Appointment> appointments, DatabaseHelper dbHelper) {
        this.context = context;
        this.appointments = appointments;
        this.dbHelper = dbHelper;
    }

    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.appointment_item, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppointmentViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.appointmentTitle.setText(appointment.getTitle());
        holder.appointmentDescription.setText(appointment.getDescription());
        holder.appointmentDate.setText(appointment.getDate());

        // Set click listener for delete button

        holder.deleteButton.setOnClickListener(v -> deleteAppointment(appointment));
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    // Funksioni për fshirjen e appointment-it
    private void deleteAppointment(Appointment appointment) {
        // Fshihet nga databaza
        dbHelper.deleteAppointment(appointment.getId());

        // Fshihet nga lista dhe rifreskohet RecyclerView
        appointments.remove(appointment);
        notifyDataSetChanged();

        // Mesazh informues
        Toast.makeText(context, "Appointment deleted", Toast.LENGTH_SHORT).show();
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder {

        TextView appointmentTitle, appointmentDescription, appointmentDate;
        Button deleteButton;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            appointmentTitle = itemView.findViewById(R.id.appointmentTitle);
            appointmentDescription = itemView.findViewById(R.id.appointmentDescription);
            appointmentDate = itemView.findViewById(R.id.appointmentDate);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}