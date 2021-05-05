package com.example.movies.data.kotlin

import com.example.movies.domain.kotlin.Movie

interface MoviesCallback {

    fun onSuccess(list: List<Movie>)

    fun onError(errorMessage: String)
}