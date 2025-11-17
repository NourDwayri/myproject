package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "starbuzz.db";
    private static final int DB_VERSION = 3;

    public StarbuzzDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create DRINK table
        db.execSQL("CREATE TABLE AVATAR (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "DESCRIPTION TEXT, "
                + "IMAGE_RESOURCE_ID INTEGER);");


        // Insert sample drinks (use your drawables)
        insertAVATAR(db, "Michael", "Beat me if you can", R.drawable.michael);
        insertAVATAR(db, "Lina", "I'm a girl, therefore I'm smart", R.drawable.lina);
        insertAVATAR(db, "Fadi", "Being a nerd is one of my coolest traits !", R.drawable.fadi);


    }

    private static void insertAVATAR(SQLiteDatabase db, String name, String description, int resourceId) {
        ContentValues cv = new ContentValues();
        cv.put("NAME", name);
        cv.put("DESCRIPTION", description);
        cv.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("AVATAR", null, cv);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simple strategy for the exam: drop and recreate so sample data stays consistent

        db.execSQL("DROP TABLE IF EXISTS AVATAR");
        onCreate(db);
    }
}
