package com.example.movies.data.java;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.movies.domain.java.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}