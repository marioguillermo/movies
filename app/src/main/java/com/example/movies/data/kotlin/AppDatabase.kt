package com.example.movies.data.kotlin

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.domain.kotlin.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}