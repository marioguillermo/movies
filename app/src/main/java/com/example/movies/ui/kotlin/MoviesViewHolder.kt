package com.example.movies.ui.kotlin

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.ItemMovieBinding
import com.example.movies.domain.kotlin.Movie

class MoviesViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun decorateWith(movie: Movie) = with(binding) {
        Glide.with(root.context).load(movie.poster).into(binding.ivPoster)
        title.text = movie.tittle
    }
}