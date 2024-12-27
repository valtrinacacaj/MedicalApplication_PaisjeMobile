package com.fiek.medicalapplication_paisjemobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Emri i bazës së të dhënave
    public static final String DATABASE_NAME = "MedicalApp.db";

    // Emri i tabelave
    public static final String USER_TABLE_NAME = "allusers";
    public static final String APPOINTMENT_TABLE_NAME = "appointments";

    // Konstruktori
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2); // Versioni i bazës 2
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        // Krijimi i tabelës për përdoruesit
        MyDatabase.execSQL("CREATE TABLE " + USER_TABLE_NAME + " (" +
                "email TEXT PRIMARY KEY, " +
                "password TEXT)");

        // Krijimi i tabelës për appointments
        MyDatabase.execSQL("CREATE TABLE " + APPOINTMENT_TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "appointmentName TEXT NOT NULL, " +
                "description TEXT, " +
                "date TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int oldVersion, int newVersion) {
        // Përditësoni bazën e të dhënave për versionin e ri
        if (oldVersion < 2) {
            MyDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + APPOINTMENT_TABLE_NAME + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "appointmentName TEXT NOT NULL, " +
                    "description TEXT, " +
                    "date TEXT NOT NULL)");
        }
    }

    // Metodat për tabelën e përdoruesve

    public boolean insertUserData(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);

        long result = MyDatabase.insert(USER_TABLE_NAME, null, contentValues);
        MyDatabase.close();
        return result != -1;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        MyDatabase.close();
        return exists;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE email = ? AND password = ?", new String[]{email, password});
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        MyDatabase.close();
        return valid;
    }

    // Metodat për tabelën e appointments

    public boolean addAppointment(String name, String description, String date) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("appointmentName", name);
        contentValues.put("description", description);
        contentValues.put("date", date);

        long result = MyDatabase.insert(APPOINTMENT_TABLE_NAME, null, contentValues);
        MyDatabase.close();
        return result != -1;
    }

    public Cursor getAllAppointments() {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        return MyDatabase.rawQuery("SELECT * FROM " + APPOINTMENT_TABLE_NAME, null);
    }

    public Cursor getAppointmentById(int id) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM " + APPOINTMENT_TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(id)});
        return cursor;
    }

    // Funksion për të përditësuar një appointment
    public boolean updateAppointment(int id, String name, String description, String date) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("appointmentName", name);
        contentValues.put("description", description);
        contentValues.put("date", date);

        int rowsAffected = MyDatabase.update(APPOINTMENT_TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(id)});
        MyDatabase.close();
        return rowsAffected > 0;
    }

    // Funksion për të fshirë një appointment
    public boolean deleteAppointment(int id) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        int rowsAffected = MyDatabase.delete(APPOINTMENT_TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        MyDatabase.close();
        return rowsAffected > 0;
    }
}
