package com.example.movies.data.kotlin

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.domain.kotlin.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM Movie WHERE tittle = :name")
    fun getMovie(name: String): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: Movie)

    @Delete
    fun delete(movie: Movie)
}