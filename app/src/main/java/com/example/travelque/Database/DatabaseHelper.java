package com.example.travelque.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper dbInstance;

    public static synchronized DatabaseHelper getInstance(Context context){
        if(dbInstance == null){
            dbInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return dbInstance;
    }

    public DatabaseHelper(@Nullable Context context) {
        super(context, "travelque.db", null, 1);
    }

    public void onConfigure(SQLiteDatabase db){
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String travel = "CREATE TABLE IF NOT EXISTS Travel(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name varchar(100), description varchar(255), image varchar(255), lang varchar(255), long varchar(255))";
        db.execSQL(travel);
        String user = "CREATE TABLE IF NOT EXISTS User(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, username varchar(100), email varchar(100),password varchar(100))";
        db.execSQL(user);
        String list = "CREATE TABLE IF NOT EXISTS List(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, notes varchar(100), user_id int, travel_id int, FOREIGN KEY(user_id) REFERENCES User(id), FOREIGN KEY(travel_id) REFERENCES Travel(id))";
        db.execSQL(list);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Travel");
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists List");
        onCreate(db);
        db.close();
    }
}
