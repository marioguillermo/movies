package com.example.movies.ui.java;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.movies.R;
import com.example.movies.data.java.Api;
import com.example.movies.data.java.DatabaseInstance;
import com.example.movies.data.java.MoviesCallback;
import com.example.movies.data.java.MoviesRepository;
import com.example.movies.data.java.RetrofitInstance;
import com.example.movies.databinding.ActivityFavoritesBinding;
import com.example.movies.domain.java.Movie;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesActivity extends AppCompatActivity {

    ExecutorService executorService = Executors.newFixedThreadPool(4);
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    MoviesRepository moviesRepository;
    MoviesAdapter adapter = new MoviesAdapter();
    private ActivityFavoritesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupToolbar();
        initRepository();
        binding.recycler.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recycler.setAdapter(adapter);
        adapter.setMovieClickListener(new MoviesAdapter.MovieClickListener() {
            @Override
            public void onMovieClicked(Movie movie) {
                moviesRepository.removeMovieFromFavorite(movie);
                adapter.removeMovie(movie);
            }
        });
        moviesRepository.getFavoriteMovies(new MoviesCallback() {
            @Override
            public void onSuccess(List<Movie> list) {
                adapter.setItems(list);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    private void setupToolbar() {
        setActionBar(binding.toolbar);
        getActionBar().setTitle(R.string.label_favorites);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initRepository() {
        moviesRepository = new MoviesRepository(
            RetrofitInstance.getRetrofitInstance().create(Api.class),
            executorService,
            mainThreadHandler,
            DatabaseInstance.getRetrofitInstance(getApplicationContext()).movieDao()
        );
    }
}