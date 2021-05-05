package com.example.movies.data.kotlin

import android.content.Context
import androidx.room.Room

class DatabaseInstanceKt(
    context: Context
) {

    val database: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "database-name-kt"
    ).build()

}