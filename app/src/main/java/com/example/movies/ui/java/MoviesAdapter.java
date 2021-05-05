package com.example.movies.ui.java;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.databinding.ItemMovieBinding;
import com.example.movies.domain.java.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {

    private List<Movie> items = new ArrayList<>();

    private MovieClickListener listener;

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviesViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.decorateWith(items.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMovieClicked(items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setMovieClickListener(MovieClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<Movie> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    public interface MovieClickListener {
        void onMovieClicked(Movie movie);
    }

    public void removeMovie(Movie movie){
        items.remove(movie);
        notifyDataSetChanged();
    }

}
