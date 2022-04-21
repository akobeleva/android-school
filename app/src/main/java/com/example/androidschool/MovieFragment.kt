package com.example.androidschool

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class MovieFragment : Fragment(R.layout.movie_layout) {
    private val movie = Movie()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageView>(R.id.poster).setImageResource(movie.posterSrc)
        view.findViewById<TextView>(R.id.name).text = movie.name
        var genres = ""
        movie.genres.forEach {
            genres = genres + it + ", "
        }
        genres = genres.substring(0, genres.length - 2)
        view.findViewById<TextView>(R.id.genres).text = genres
        var countries = ""
        movie.countries.forEach {
            countries = countries + it + ", "
        }
        countries = countries.substring(0, countries.length - 2)
        view.findViewById<TextView>(R.id.countries).text = countries
        view.findViewById<TextView>(R.id.year).text = movie.year.toString()
        view.findViewById<TextView>(R.id.description).text = movie.description
    }
}