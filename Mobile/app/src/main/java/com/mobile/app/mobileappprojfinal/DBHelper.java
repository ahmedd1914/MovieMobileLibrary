package com.mobile.app.mobileappprojfinal;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "MobileAppProjFinal.db";
    private static final int DATABASE_VERSION = 2;

    private static final String USER_TABLE_NAME = "user";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    private static final String MOVIE_TABLE_NAME = "my_movies";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "movie_title";
    private static final String COLUMN_AUTHOR = "movie_author";
    private static final String COLUMN_DESCRIPTION = "movie_description";

    DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUsersTable(db);
        createMoviesTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE_NAME);
        onCreate(db);
    }

    // User-related methods

    boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        long ins = db.insert(USER_TABLE_NAME, null, contentValues);
        return ins != -1;
    }

    boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " + COLUMN_USERNAME + "=?",
                new String[]{username});
        return cursor.getCount() <= 0;
    }

    boolean usernamePassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password});
        return cursor.getCount() > 0;
    }



    long addMovie(String title, String author, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_DESCRIPTION, description);

        long result = db.insert(MOVIE_TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
        return result;
    }


    Cursor readAllData() {
        String query = "SELECT * FROM " + MOVIE_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String title, String author, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_DESCRIPTION, description);
        long result = db.update(MOVIE_TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(MOVIE_TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + MOVIE_TABLE_NAME);
    }

    private void createUsersTable(SQLiteDatabase db) {
        String query = "CREATE TABLE " + USER_TABLE_NAME +
                " (" + COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT);";
        db.execSQL(query);
    }

    private void createMoviesTable(SQLiteDatabase db) {
        String query = "CREATE TABLE " + MOVIE_TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT);";
        db.execSQL(query);
    }



}
