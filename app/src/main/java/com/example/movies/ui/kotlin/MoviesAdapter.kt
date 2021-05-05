package com.example.movies.ui.kotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.ItemMovieBinding
import com.example.movies.domain.kotlin.Movie

class MoviesAdapter : RecyclerView.Adapter<MoviesViewHolder>() {

    val list: MutableList<Movie> = mutableListOf()

    var listener: (Movie) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.decorateWith(list[position])
        holder.itemView.setOnClickListener {
            listener(list[position])
        }
    }

    override fun getItemCount() = list.size

    fun setItems(newItems: List<Movie>) {
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
    }

    fun removeMovie(movie: Movie) {
        list.remove(movie)
        notifyDataSetChanged()
    }
}