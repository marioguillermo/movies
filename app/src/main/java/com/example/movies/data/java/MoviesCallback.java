package com.example.movies.data.java;

import com.example.movies.domain.java.Movie;

import java.util.List;

public interface MoviesCallback {

    void onSuccess(List<Movie> list);

    void onError(String errorMessage);

}