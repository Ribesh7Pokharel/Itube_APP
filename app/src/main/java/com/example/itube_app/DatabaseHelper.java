package com.example.itube_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "iTube.db";
    private static final int DATABASE_VERSION = 1;

    // User table creation SQL statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, email TEXT, logged_in INTEGER DEFAULT 0);";
    // Playlist table creation SQL statement
    private static final String CREATE_TABLE_PLAYLIST = "CREATE TABLE playlists (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, video_url TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_PLAYLIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade policy here
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS playlists");
        onCreate(db);
    }

    // Method to add a user to the database
    public boolean addUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password); // Remember to hash in real applications
        values.put("email", email);
        long result = db.insert("users", null, values);
        db.close();
        return result != -1;
    }

    // In DatabaseHelper.java

    public boolean isUserLoggedIn() {
        // Placeholder for session check logic
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM users WHERE logged_in = 1 LIMIT 1", null);
        boolean isLoggedIn = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isLoggedIn;
    }

    // In DatabaseHelper.java

    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { "id" };
        String selection = "username=? AND password=?";
        String[] selectionArgs = { username, password };  // In real applications, password should be hashed
        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);
        boolean loginSuccessful = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return loginSuccessful;
    }

    public void setUserLoggedIn(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("logged_in", 1);
        db.update("users", values, "username=?", new String[] { username });
        db.close();
    }

    // In DatabaseHelper.java

    public List<String> getUserPlaylists(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> playlists = new ArrayList<>();
        Cursor cursor = db.query("playlists", new String[] {"video_url"}, "user_id=?", new String[] {String.valueOf(userId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                playlists.add(cursor.getString(cursor.getColumnIndex("video_url")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return playlists;
    }

    public boolean deleteVideoFromPlaylist(int userId, String videoUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete("playlists", "user_id=? AND video_url=?", new String[] {String.valueOf(userId), videoUrl});
        db.close();
        return deletedRows > 0;
    }

    // In DatabaseHelper.java

    public List<String> getAllPlaylists(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> playlists = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT DISTINCT playlist_name FROM playlists WHERE user_id=?", new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                playlists.add(cursor.getString(0)); // Assuming 'playlist_name' is a column in your 'playlists' table
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return playlists;
    }

    public List<String> getPlaylistDetails(String playlistName, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> videoUrls = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT video_url FROM playlists WHERE user_id=? AND playlist_name=?", new String[]{String.valueOf(userId), playlistName});

        if (cursor.moveToFirst()) {
            do {
                videoUrls.add(cursor.getString(0)); // Fetch the URLs of videos in the playlist
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return videoUrls;
    }

    // In DatabaseHelper.java

    public boolean addVideoToPlaylist(int userId, String videoUrl, String playlistName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("video_url", videoUrl);
        values.put("playlist_name", playlistName);  // Assuming you have a 'playlist_name' column to categorize playlists
        long result = db.insert("playlists", null, values);
        db.close();
        return result != -1;
    }

}
