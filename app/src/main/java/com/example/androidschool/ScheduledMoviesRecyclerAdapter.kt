package com.example.androidschool

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidschool.model.dto.Movie
import com.example.androidschool.model.dto.ScheduledMovie

class ScheduledMoviesRecyclerAdapter(private val fragmentActivity: FragmentActivity) :
    RecyclerView.Adapter<ScheduledMoviesRecyclerAdapter.ScheduledMovieViewHolder>() {
    private val scheduledMoviesList = mutableListOf<ScheduledMovie>()

    class ScheduledMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieTitleTextView: TextView = itemView.findViewById(R.id.movieTitle)
        var scheduledDate: TextView = itemView.findViewById(R.id.date)
        var movieImageView: ImageView = itemView.findViewById(R.id.movieImageView)
        var movieCardView: CardView = itemView.findViewById(R.id.movieCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduledMovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ScheduledMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduledMovieViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.movieTitleTextView.text = movie.name
        holder.movieYearTextView.text = movie.year
        holder.movieDescriptionTextView.text = movie.description
        Glide
            .with(holder.movieImageView.context)
            .load(movie.poster?.url)
            .into(holder.movieImageView)
        holder.movieCardView.setOnClickListener { clickListener(movie) }
    }

    private fun clickListener(movie: Movie) {
        val movieFragment = MovieFragment.newInstance(movie.id)
        fragmentActivity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, movieFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun getItemCount(): Int {
        return scheduledMoviesList.size
    }

//    fun reload(data: List<Movie>?) {
//        moviesList.clear()
//        data?.let { moviesList.addAll(it) }
//        notifyDataSetChanged()
//    }
}