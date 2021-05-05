package com.example.movies.ui.java;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.movies.data.java.Api;
import com.example.movies.data.java.DatabaseInstance;
import com.example.movies.data.java.MoviesCallback;
import com.example.movies.data.java.MoviesRepository;
import com.example.movies.data.java.RetrofitInstance;
import com.example.movies.databinding.ActivityMainBinding;
import com.example.movies.domain.java.Movie;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ExecutorService executorService = Executors.newFixedThreadPool(4);
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    MoviesRepository moviesRepository;
    MoviesAdapter adapter = new MoviesAdapter();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        moviesRepository = new MoviesRepository(
            RetrofitInstance.getRetrofitInstance().create(Api.class),
            executorService,
            mainThreadHandler,
            DatabaseInstance.getRetrofitInstance(getApplicationContext()).movieDao()
        );
        binding.recycler.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recycler.setAdapter(adapter);
        moviesRepository.getMovies(new MoviesCallback() {
            @Override
            public void onSuccess(List<Movie> list) {
                adapter.setItems(list);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
        adapter.setMovieClickListener(new MoviesAdapter.MovieClickListener() {
            @Override
            public void onMovieClicked(Movie movie) {
                startDetailsActivity(movie);
            }
        });
        binding.btFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FavoritesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startDetailsActivity(Movie movie){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE_EXTRA, movie);
        startActivity(intent);
    }
}