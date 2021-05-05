package com.example.movies.ui.java;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import com.bumptech.glide.Glide;
import com.example.movies.R;
import com.example.movies.data.java.Api;
import com.example.movies.data.java.DatabaseInstance;
import com.example.movies.data.java.MovieCallback;
import com.example.movies.data.java.MoviesRepository;
import com.example.movies.data.java.RetrofitInstance;
import com.example.movies.databinding.ActivityDetailBinding;
import com.example.movies.domain.java.Movie;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "MOVIE_EXTRA";
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    MoviesRepository moviesRepository;
    private ActivityDetailBinding binding;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        initRepository();
        movie = getIntent().getParcelableExtra(MOVIE_EXTRA);
        setContentView(binding.getRoot());
        moviesRepository.getMovie(movie, new MovieCallback() {
            @Override
            public void onDataBaseResponse(Movie movie) {
                if (movie == null)
                    setUpAddButton();
                else
                    setupDeleteButton();
                setupToolbar();
                setupViews();
            }
        });
    }

    private void setUpAddButton() {
        binding.btFavorites.setText(R.string.label_add_to_favorites);
        binding.btFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moviesRepository.addMovieToFavorite(movie);
                setupDeleteButton();
            }
        });
    }

    private void setupDeleteButton() {
        binding.btFavorites.setText(R.string.label_remove_from_favorites);
        binding.btFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moviesRepository.removeMovieFromFavorite(movie);
                setUpAddButton();
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

    private void setupViews() {
        Glide.with(this).load(movie.poster).centerCrop().into(binding.ivPoster);
        binding.tvDirector.setText(getString(R.string.label_director, movie.director));
        binding.tvSummary.setText(movie.summary);
    }

    private void setupToolbar() {
        setActionBar(binding.toolbar);
        getActionBar().setTitle(movie.tittle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}