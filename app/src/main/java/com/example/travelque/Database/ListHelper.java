package com.example.travelque.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.travelque.Models.List;

import java.util.Vector;

public class ListHelper {

    private static final String TABLE_LIST = "List";
    private Context listCtx;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public ListHelper(Context listCtx) {
        this.listCtx = listCtx;
    }

    public void open() throws SQLException {
        databaseHelper = new DatabaseHelper(listCtx);
        db = databaseHelper.getWritableDatabase();
    }

    public void close() throws SQLException {
        databaseHelper.close();
    }

    public void addList(String notes, Integer userId, Integer travelId) {
        String add = "SELECT * FROM " + TABLE_LIST;
        Cursor cursor = db.rawQuery(add, null);

        if (cursor != null && cursor.moveToLast()) {
            int tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            tempID++;
            add = "INSERT INTO " + TABLE_LIST + " (id, notes, user_id, travel_id) VALUES (" + tempID + ", '" + notes + "', '" + userId + "', '" + travelId + "')";
            db.execSQL(add);
            return;
        }else {
            add = "INSERT INTO " + TABLE_LIST + " (id, notes, user_id, travel_id) VALUES (" + 1 + ", '" + notes + "', '" + userId + "', '" + travelId + "')";
            db.execSQL(add);
        }
    }

    public Vector<List> viewList(Integer userId) {
        Vector<List> vList = new Vector<>();
        String view = "SELECT * FROM " + TABLE_LIST + " WHERE user_id = " + userId;
        Cursor cursor = db.rawQuery(view, null);

        List list;
        String tempNotes;
        Integer tempID, tempUserID, tempTravelID;

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                tempNotes = cursor.getString(cursor.getColumnIndexOrThrow("notes"));
                tempUserID = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                tempTravelID = cursor.getInt(cursor.getColumnIndexOrThrow("travel_id"));

                list = new List(tempID, tempNotes, tempUserID, tempTravelID);
                vList.add(list);
            }
        }

        cursor.close();
        return vList;
    }

    public void deleteList(Integer id) {
        String delete = "DELETE FROM " + TABLE_LIST + " WHERE id = " + id;
        db.execSQL(delete);
    }

    public void updateList(Integer id, String notes, Integer user_id, Integer travel_id) {
        String update = "UPDATE " + TABLE_LIST + " SET notes = '" + notes + "', user_id = '" + user_id + "', travel_id = '" + travel_id + "' WHERE id = " + id;
        db.execSQL(update);
    }

}
