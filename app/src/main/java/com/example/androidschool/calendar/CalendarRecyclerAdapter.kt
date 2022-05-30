package com.example.androidschool.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidschool.MovieFragment
import com.example.androidschool.R
import com.example.androidschool.model.dto.Movie
import java.text.DateFormat
import java.util.*

class CalendarRecyclerAdapter(private val fragmentActivity: FragmentActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_DATE = 1
    private val TYPE_MOVIE = 2

    private val dataList = mutableListOf<Any>()

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateTitleTextView: TextView = itemView.findViewById(R.id.scheduledDate)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieTitleTextView: TextView = itemView.findViewById(R.id.movieTitle)
        var movieDescriptionTextView: TextView = itemView.findViewById(R.id.movieDescription)
        var movieYearTextView: TextView = itemView.findViewById(R.id.movieYear)
        var movieImageView: ImageView = itemView.findViewById(R.id.movieImageView)
        var movieCardView: CardView = itemView.findViewById(R.id.movieCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_DATE) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.scheduled_date_item, parent, false)
            DateViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
            MovieViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_DATE) {
            holder as DateViewHolder
            val date = dataList[position] as Date
            holder.dateTitleTextView.text =
                DateFormat.getDateInstance(DateFormat.MEDIUM).format(date)
        } else {
            holder as MovieViewHolder
            val movie = dataList[position] as Movie
            holder.movieTitleTextView.text = movie.name
            holder.movieYearTextView.text = movie.year
            holder.movieDescriptionTextView.text = movie.description
            Glide
                .with(holder.movieImageView.context)
                .load(movie.poster?.url)
                .into(holder.movieImageView)
            holder.movieCardView.setOnClickListener { clickListener(movie) }
        }
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
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataList[position] is Date) {
            TYPE_DATE
        } else {
            TYPE_MOVIE
        }
    }

    fun reload(data: List<Any>) {
        dataList.clear()
        data.let { dataList.addAll(it) }
        notifyDataSetChanged()
    }
}