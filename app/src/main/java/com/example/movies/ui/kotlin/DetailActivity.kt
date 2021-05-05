package com.example.movies.ui.kotlin

import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.data.kotlin.Api
import com.example.movies.data.kotlin.DatabaseInstanceKt
import com.example.movies.data.kotlin.MoviesRepositoryKt
import com.example.movies.data.kotlin.RetrofitInstance
import com.example.movies.databinding.ActivityDetailBinding
import com.example.movies.domain.kotlin.Movie
import java.util.concurrent.Executors


const val MOVIE_EXTRA = "MOVIE_EXTRA"

class DetailActivity : AppCompatActivity() {

    private val executor = Executors.newFixedThreadPool(4)
    private val mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    lateinit var moviesRepository: MoviesRepositoryKt
    private lateinit var binding: ActivityDetailBinding
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        moviesRepository = MoviesRepositoryKt(
            RetrofitInstance.retrofit.create(Api::class.java),
            DatabaseInstanceKt(this).database.movieDao(),
            executor,
            mainThreadHandler
        )
        movie = intent.getParcelableExtra(MOVIE_EXTRA) ?: throw IllegalArgumentException()
        moviesRepository.getMovie(movie) {
            setUpView(it != null)
        }
    }

    private fun setUpView(isFavorite: Boolean) {
        if (isFavorite.not()) setUpAddButton() else setupDeleteButton()
        setupToolbar()
        setupViews()
    }

    private fun setupToolbar() {
        setActionBar(binding.toolbar)
        actionBar?.run {
            title = movie.tittle
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupViews() = with(binding) {
        Glide.with(root.context).load(movie.poster).into(ivPoster)
        tvDirector.text = getString(R.string.label_director, movie.director)
        tvSummary.text = movie.summary
    }

    private fun setupDeleteButton() = with(binding) {
        btFavorites.text = getString(R.string.label_remove_from_favorites)
        btFavorites.setOnClickListener {
            moviesRepository.removeMovieFromFavorites(movie)
            setUpView(false)
        }
    }

    private fun setUpAddButton() = with(binding) {
        btFavorites.text = getString(R.string.label_add_to_favorites)
        btFavorites.setOnClickListener {
            moviesRepository.addMovieToFavorites(movie)
            setUpView(true)
        }
    }

}