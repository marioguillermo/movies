package com.example.movies.data.kotlin

import com.example.movies.domain.kotlin.Movie
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("simple-api/static_list_example.json")
    fun getMovies(): Call<List<Movie>>
}