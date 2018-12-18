package com.example.android.exampleapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.exampleapp.DataContract.DataEntry.COLUMN_CATEGORY;
import static com.example.android.exampleapp.DataContract.DataEntry.COLUMN_TIME_STAMP;
import static com.example.android.exampleapp.DataContract.DataEntry.COLUMN_TITLE;
import static com.example.android.exampleapp.DataContract.DataEntry.TABLE_NAME;
import static com.example.android.exampleapp.DataContract.DataEntry._ID;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dataobjs.db";

    private static final int DATABASE_VERSION = 6;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT  , " +
                COLUMN_CATEGORY + " TEXT , " +
                COLUMN_TIME_STAMP + " TEXT " + ");";

        db.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
