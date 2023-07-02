package com.example.travelque.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.travelque.Models.User;

public class UserHelper {

    private static final String TABLE_USER = "User";
    private Context userContext;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public UserHelper(Context userContext) {
        this.userContext = userContext;
    }

    public void open() throws SQLException {
        dbHelper = new DatabaseHelper(userContext);
        db = dbHelper.getWritableDatabase();
    }

    public void close() throws SQLException {
        dbHelper.close();
    }

    public void addUser(String username, String email, String password) {
        String add = "Select id from " + TABLE_USER;
        Cursor cursor = db.rawQuery(add, null);

        if (cursor != null && cursor.moveToLast()) {
            int tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            tempID++;
            add = "INSERT INTO " + TABLE_USER + " (id, username, email, password) VALUES (" + tempID + ", '" + username + "', '" + email + "', '" + password + "')";
            db.execSQL(add);
            return;
        }else {
            add = "INSERT INTO " + TABLE_USER + " (id, username, email, password) VALUES (" + 1 + ", '" + username + "', '" + email + "', '" + password + "')";
            db.execSQL(add);
        }

        cursor.close();
    }

    public Boolean checkUsername(String username){
        String search = "Select * from " + TABLE_USER + " where username = ?";

        Cursor cursor = db.rawQuery(search, new String[]{username});
        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean checkAccount(String username,String password){
        String search = "Select * from " + TABLE_USER + " where username = ? and password = ?";

        Cursor cursor = db.rawQuery(search,new String[]{username,password});
        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public User fetchUser(String username){
        String view = "Select * from " + TABLE_USER + " where username = ? limit 1";

        Cursor cursor = db.rawQuery(view,new String[]{username});

        cursor.moveToFirst();

        if(cursor.getCount() <= 0){
            return null;
        }

        User user;
        String tempUsername, tempEmail, tempPassword;
        Integer tempID;

        tempID = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        tempUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
        tempEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
        tempPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));
        user = new User(tempID, tempUsername, tempEmail, tempPassword);

        return user;
    }

    public void updateUser(int id, String username, String email, String password) {
        String query = "UPDATE " + TABLE_USER + " SET username = '" + username + "', email = '" + email + "', password = '" + password + "' WHERE id = " + id;
        db.execSQL(query);
    }

}
