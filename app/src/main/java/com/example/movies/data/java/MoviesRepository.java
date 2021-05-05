package com.example.movies.data.java;

import android.os.Handler;

import com.example.movies.domain.java.Movie;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    private final Executor executor;
    private final Handler handler;
    private Api api;
    private MovieDao dao;

    public MoviesRepository(
        Api api,
        Executor executor,
        Handler handler,
        MovieDao dao
    ) {
        this.api = api;
        this.executor = executor;
        this.handler = handler;
        this.dao = dao;
    }

    public void getMovies(MoviesCallback callback) {
        Call<List<Movie>> call = api.getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Hubo un error!");
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                callback.onError("Hubo un error!");
            }
        });
    }


    public void addMovieToFavorite(Movie movie) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertAll(movie);
            }
        });
    }

    public void removeMovieFromFavorite(Movie movie) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(movie);
            }
        });
    }

    public void getFavoriteMovies(MoviesCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Movie> list = dao.getAll();
                postSuccessResult(callback, list);
            }
        });
    }

    public void getMovie(Movie movie, MovieCallback movieCallback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                postResult(movieCallback, dao.getMovie(movie.tittle));
            }
        });
    }

    private void postSuccessResult(MoviesCallback callback, List<Movie> list) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(list);
            }
        });
    }

    private void postResult(MovieCallback callback, Movie movie) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onDataBaseResponse(movie);
            }
        });
    }
}