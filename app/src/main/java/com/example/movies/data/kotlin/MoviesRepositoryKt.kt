package com.example.movies.data.kotlin

import android.os.Handler
import com.example.movies.domain.kotlin.Movie
import java.util.concurrent.Executor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepositoryKt(
    private val api: Api,
    private val dao: MovieDao,
    private val executor: Executor,
    private val handler: Handler
) {

    fun getMovies(callback: MoviesCallback) {
        val call = api.getMovies()
        call.enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.isSuccessful && response.body() != null) {
                    callback.onSuccess(response.body()!!)
                } else {
                    callback.onError("Hubo un error")
                }
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                callback.onError("Hubo un error")
            }
        })
    }

    fun addMovieToFavorites(movie: Movie) = executor.execute {
        dao.insertAll(movie)
    }

    fun removeMovieFromFavorites(movie: Movie) = executor.execute {
        dao.delete(movie)
    }

    fun getMovie(movie: Movie, callback: (Movie?) -> Unit) = executor.execute {
        val result = dao.getMovie(movie.tittle)
        handler.post { callback(result) }
    }

    fun getFavoriteMovies(
        callback: MoviesCallback
    ) = executor.execute {
        postSuccessful(callback, dao.getAll())
    }

    private fun postSuccessful(
        callback: MoviesCallback,
        list: List<Movie>
    ) = handler.post {
        callback.onSuccess(list)
    }

}