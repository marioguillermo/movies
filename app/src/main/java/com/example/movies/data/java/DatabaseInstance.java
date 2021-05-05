package com.example.movies.data.java;

import android.content.Context;

import androidx.room.Room;

public class DatabaseInstance {

    private static AppDatabase database;

    public static AppDatabase getRetrofitInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(
                context,
                AppDatabase.class,
                "database-name"
            ).build();
        }
        return database;
    }
}