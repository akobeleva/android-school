package com.example.androidschool

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidschool.model.dto.Movie

class MoviesRecyclerAdapter(private val listener: OnMovieListener) :
    RecyclerView.Adapter<MoviesRecyclerAdapter.MovieViewHolder>() {
    private val moviesList = mutableListOf<Movie>()

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieTitleTextView: TextView = itemView.findViewById(R.id.movieTitle)
        var movieDescriptionTextView: TextView = itemView.findViewById(R.id.movieDescription)
        var movieYearTextView: TextView = itemView.findViewById(R.id.movieYear)
        var movieImageView: ImageView = itemView.findViewById(R.id.movieImageView)
        var movieCardView: CardView = itemView.findViewById(R.id.movieCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.movieTitleTextView.text = movie.name
        holder.movieYearTextView.text = movie.year
        holder.movieDescriptionTextView.text = movie.description
        Glide
            .with(holder.movieImageView.context)
            .load(movie.poster?.url)
            .into(holder.movieImageView)
        holder.movieCardView.setOnClickListener { listener.onClick(movie.id) }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun reload(data: List<Movie>?) {
        moviesList.clear()
        data?.let { moviesList.addAll(it) }
        notifyDataSetChanged()
    }

    interface OnMovieListener {
        fun onClick(movieId: Long)
    }
}