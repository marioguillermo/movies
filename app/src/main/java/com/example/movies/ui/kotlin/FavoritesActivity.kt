package com.example.movies.ui.kotlin

import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.R
import com.example.movies.data.kotlin.Api
import com.example.movies.data.kotlin.DatabaseInstanceKt
import com.example.movies.data.kotlin.MoviesCallback
import com.example.movies.data.kotlin.MoviesRepositoryKt
import com.example.movies.data.kotlin.RetrofitInstance
import com.example.movies.databinding.ActivityFavoritesBinding
import com.example.movies.domain.kotlin.Movie
import java.util.concurrent.Executors

class FavoritesActivity : AppCompatActivity() {

    private val executor = Executors.newFixedThreadPool(4)
    private val mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    lateinit var moviesRepository: MoviesRepositoryKt
    private lateinit var binding: ActivityFavoritesBinding
    private val adapter = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        moviesRepository = MoviesRepositoryKt(
            RetrofitInstance.retrofit.create(Api::class.java),
            DatabaseInstanceKt(this).database.movieDao(),
            executor,
            mainThreadHandler
        )
        binding.recycler.layoutManager = GridLayoutManager(this, 2)
        binding.recycler.adapter = adapter
        moviesRepository.getFavoriteMovies(object : MoviesCallback {
            override fun onSuccess(list: List<Movie>) {
                adapter.setItems(list)
            }

            override fun onError(errorMessage: String) {
            }
        })
        adapter.listener = {
            adapter.removeMovie(it)
            moviesRepository.removeMovieFromFavorites(it)
        }
    }

    private fun setupToolbar() {
        setActionBar(binding.toolbar)
        actionBar?.run {
            setTitle(R.string.label_favorites)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}