package com.example.movies.ui.java;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.R;
import com.example.movies.databinding.ItemMovieBinding;
import com.example.movies.domain.java.Movie;

public class MoviesViewHolder extends RecyclerView.ViewHolder {

    ItemMovieBinding itemMovieBinding;

    public MoviesViewHolder(ItemMovieBinding binding) {
        super(binding.getRoot());
        itemMovieBinding = binding;
    }

    public void decorateWith(Movie model) {
        Glide.with(itemView.getContext()).load(model.poster).into(itemMovieBinding.ivPoster);
        itemView.getContext().getString(R.string.app_name);
        itemMovieBinding.title.setText(model.tittle);
    }
}
