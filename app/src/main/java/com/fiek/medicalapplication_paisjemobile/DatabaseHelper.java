


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
    public static final String TABLE_NAME = "allusers"; // Emri i tabelës

    // Konstruktori
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        // Krijimi i tabelës
        MyDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                "email TEXT PRIMARY KEY, " +
                "password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int oldVersion, int newVersion) {
        // Drop tabela nëse ekziston
        MyDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(MyDatabase);
    }

    // Metoda për të shtuar të dhëna
    public Boolean insertData(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);

        long result = MyDatabase.insert(TABLE_NAME, null, contentValues);
        return result != -1; // Kthe `true` nëse futja është e suksesshme
    }

    // Metoda për të kontrolluar nëse email-i ekziston
    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Metoda për të kontrolluar email-in dhe password-in
    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE email = ? AND password = ?", new String[]{email, password});
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }
}





