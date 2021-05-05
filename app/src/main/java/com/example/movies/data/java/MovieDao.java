package com.example.movies.data.java;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movies.domain.java.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movie")
    List<Movie> getAll();

    @Query("SELECT * FROM Movie WHERE tittle = :name")
    Movie getMovie(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Movie... movies);

    @Delete
    void delete(Movie movie);

}
