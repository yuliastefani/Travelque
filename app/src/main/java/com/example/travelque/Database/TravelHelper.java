package com.example.travelque.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.travelque.Models.Travel;

import java.util.Vector;

public class TravelHelper {
    private static final String TABLE_TRAVEL = "Travel";
    private Context travelCtx;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public TravelHelper(Context travelCtx) {
        this.travelCtx = travelCtx;
    }

    public void open() throws SQLException {
        databaseHelper = new DatabaseHelper(travelCtx);
        db = databaseHelper.getWritableDatabase();
    }

    public void close() throws SQLException {
        databaseHelper.close();
    }

    public void addTravel(String name, String description, String image, String lang, String longi) {
        String add = "SELECT * FROM " + TABLE_TRAVEL;
        Cursor cursor = db.rawQuery(add, null);
        ContentValues contentValues = new ContentValues();

        int travelID = 0;

        if (cursor.getCount() != 0 && cursor.moveToLast()) {
            int tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            tempID++;
            travelID = tempID;
        }else {
            travelID = 1;
        }

        contentValues.put("id", travelID);
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("image", image);
        contentValues.put("lang", lang);
        contentValues.put("long", longi);

        db.insert(TABLE_TRAVEL, null, contentValues);

        cursor.close();

    }

    public Vector<Travel> viewTravel() {
        Vector<Travel> vTravel = new Vector<>();
        String view = "SELECT * FROM " + TABLE_TRAVEL;
        Cursor cursor = db.rawQuery(view, null);

        Travel travel;
        String tempName, tempDescription, tempImage;
        Double tempLang, tempLongi;
        Integer tempID;

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                tempName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                tempDescription = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                tempImage = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                tempLang = cursor.getDouble(cursor.getColumnIndexOrThrow("lang"));
                tempLongi = cursor.getDouble(cursor.getColumnIndexOrThrow("long"));

                travel = new Travel(tempID, tempName, tempDescription, tempImage, tempLang, tempLongi);
                vTravel.add(travel);
            }
        }

        cursor.close();
        return vTravel;
    }

    public boolean validateTravel(String name){
        String validate = "SELECT * FROM " + TABLE_TRAVEL + " WHERE name = '" + name + "'";
        Cursor cursor = db.rawQuery(validate, null);

        return cursor.getCount() > 0;
    }
}
