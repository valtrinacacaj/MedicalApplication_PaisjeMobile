package com.fiek.medicalapplication_paisjemobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import org.mindrot.jbcrypt.BCrypt;
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MedicalApp.db";
    public static final String TABLE_USER = "allusers";
    public static final String TABLE_APPOINTMENTS = "appointments";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "surname TEXT," +
                "age INTEGER," +
                "email TEXT PRIMARY KEY," +
                "password TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_APPOINTMENTS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "appointment_name TEXT," +
                "description TEXT," +
                "date TEXT)");


}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        onCreate(db);
    }

    public Boolean insertData(String name, String surname, int age, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("surname", surname);
        contentValues.put("age", age);
        contentValues.put("email", email);

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        contentValues.put("password", hashedPassword);

        long result = db.insert(TABLE_USER, null, contentValues);
        return result != -1;
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT password FROM " + TABLE_USER + " WHERE email = ?", new String[]{email});

        if (cursor.moveToFirst()) {
            String storedHashedPassword = cursor.getString(0);
            cursor.close();
            return BCrypt.checkpw(password, storedHashedPassword);
        }

        cursor.close();
        return false;
    }

    public Cursor getFullUserData(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE email = ?";
        return db.rawQuery(query, new String[]{email});
    }

    public boolean updateUserData(String name, String surname, String age, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("surname", surname);
        contentValues.put("age", age);

        int result = db.update(TABLE_USER, contentValues, "email = ?", new String[]{email});
        return result > 0;
    }
    public Cursor getAllAppointments() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Përputhja e kolonave me ato të tabelës
        return db.rawQuery("SELECT appointment_name, description, date FROM " + TABLE_APPOINTMENTS, null);
    }


    public boolean insertAppointment(String appointmentName, String description, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("appointment_name", appointmentName);
        contentValues.put("description", description);
        contentValues.put("date", date);

        long result = db.insert(TABLE_APPOINTMENTS, null, contentValues);
        return result != -1;
    }


}
