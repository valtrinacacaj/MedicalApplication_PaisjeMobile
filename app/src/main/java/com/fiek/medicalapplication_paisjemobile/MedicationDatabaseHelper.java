//package com.fiek.medicalapplication_paisjemobile;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import androidx.annotation.Nullable;
//
//public class MedicationDatabaseHelper extends SQLiteOpenHelper {
//
//    // Emri i bazës së të dhënave
//    public static final String DATABASE_NAME = "MedicalApp.db";
//    public static final String TABLE_NAME = "allusers"; // Emri i tabelës
//    public static final String TABLE_MEDICATION = "medications"; // Tabela për medikamentet
//
//    // Konstruktori
//    public MedicationDatabaseHelper(@Nullable Context context) {
//        super(context, DATABASE_NAME, null, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase MyDatabase) {
//        // Krijimi i tabelës së përdoruesve
//        MyDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
//                "email TEXT PRIMARY KEY, " +
//                "password TEXT)");
//
//        // Krijimi i tabelës së medikamenteve
//        MyDatabase.execSQL("CREATE TABLE " + TABLE_MEDICATION + " (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "name TEXT, " +
//                "dosage TEXT, " +
//                "time TEXT)");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase MyDatabase, int oldVersion, int newVersion) {
//        // Drop tabelat nëse ekzistojnë
//        MyDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        MyDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICATION);
//        onCreate(MyDatabase);
//    }
//
//    // Metoda për të shtuar një medikament
//    public boolean insertMedication(String name, String dosage, String time) {
//        SQLiteDatabase MyDatabase = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("name", name);
//        contentValues.put("dosage", dosage);
//        contentValues.put("time", time);
//
//        long result = MyDatabase.insert(TABLE_MEDICATION, null, contentValues);
//        return result != -1; // Kthe `true` nëse futja është e suksesshme
//    }
//
//    // Metoda për të fshirë një medikament
//    public boolean deleteMedication(int id) {
//        SQLiteDatabase MyDatabase = this.getWritableDatabase();
//        int result = MyDatabase.delete(TABLE_MEDICATION, "id = ?", new String[]{String.valueOf(id)});
//        return result > 0; // Kthe `true` nëse fshirja është e suksesshme
//    }
//
//    // Metoda për të përditësuar një medikament
//    public boolean updateMedication(int id, String name, String dosage, String time) {
//        SQLiteDatabase MyDatabase = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("name", name);
//        contentValues.put("dosage", dosage);
//        contentValues.put("time", time);
//
//        int result = MyDatabase.update(TABLE_MEDICATION, contentValues, "id = ?", new String[]{String.valueOf(id)});
//        return result > 0; // Kthe `true` nëse përditësimi është i suksesshëm
//    }
//
//    // Metoda për të marrë të gjitha medikamentet
//    public Cursor getAllMedications() {
//        SQLiteDatabase MyDatabase = this.getReadableDatabase();
//        return MyDatabase.rawQuery("SELECT * FROM " + TABLE_MEDICATION, null);
//    }
//}
