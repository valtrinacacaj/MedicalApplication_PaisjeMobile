package com.fiek.medicalapplication_paisjemobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Inicimi i ndihmësit të databazës dhe ndihmësit të njoftimeve
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        NotificationHelper notificationHelper = new NotificationHelper(context);

        // Formati për datën e sotme
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String today = dateFormat.format(new Date());

        // Merr të dhënat për takimet e sotme
        Cursor cursor = dbHelper.getAppointmentsForToday(today);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Kontrollo nëse kolona ekziston
                int appointmentNameIndex = cursor.getColumnIndex("appointment_name");
                if (appointmentNameIndex != -1) {
                    String appointmentName = cursor.getString(appointmentNameIndex);
                    notificationHelper.sendNotification("Appointment Reminder", "You have an appointment: " + appointmentName);
                } else {
                    // Nëse kolona nuk ekziston, logojmë një gabim
                    Log.e("NotificationReceiver", "appointment_name column not found in cursor");
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}