package com.fiek.medicalapplication_paisjemobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import org.mindrot.jbcrypt.BCrypt;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MedicalApp.db";
    public static final String TABLE_USER = "allusers";
    public static final String TABLE_APPOINTMENTS = "appointments";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "surname TEXT," +
                "age INTEGER," +
                "email TEXT UNIQUE," +
                "password TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_APPOINTMENTS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "appointment_name TEXT," +
                "description TEXT," +
                "date TEXT," +
                "user_id INTEGER," +
                "FOREIGN KEY(user_id) REFERENCES " + TABLE_USER + "(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_APPOINTMENTS + " ADD COLUMN user_id INTEGER");
        }
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

    public boolean insertAppointment(String appointmentName, String description, String date, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("appointment_name", appointmentName);
        contentValues.put("description", description);
        contentValues.put("date", date);
        contentValues.put("user_id", userId);

        long result = db.insert(TABLE_APPOINTMENTS, null, contentValues);
        return result != -1;
    }

    public Cursor getAllAppointments(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_APPOINTMENTS,
                null,
                "user_id = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                "date ASC"
        );
    }
    public Cursor getAppointmentsForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE user_id = ? ORDER BY date ASC", new String[]{String.valueOf(userId)});
    }

    public int getUserIdFromEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM " + TABLE_USER + " WHERE email = ?", new String[]{email});

        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(0);
            cursor.close();
            return userId;
        }
        cursor.close();
        return -1;
    }


}
