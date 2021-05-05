package com.example.movies.data.kotlin

import com.example.movies.domain.java.Movie

interface MovieCallback {
    fun onDataBaseResponse(movie: Movie?)
}