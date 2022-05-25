package com.example.androidschool

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.androidschool.model.dto.Movie
import com.example.androidschool.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieFragment : Fragment(R.layout.movie_layout) {


    companion object {
        fun newInstance(movieId: Long): MovieFragment =
            MovieFragment().apply {
                val bundle = Bundle()
                bundle.putLong("movieId", movieId)
                arguments = bundle
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = requireArguments().getLong("movieId")
        NetworkService.getInstance()
            ?.getRetrofitServiceApi()
            ?.getMovie(movieId)
            ?.enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie?>, response: Response<Movie?>) {
                    val movie: Movie? = response.body()
                    if (movie != null) {
                        showMovie(view, movie)
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    private fun showMovie(view: View, movie: Movie) {
        view.findViewById<TextView>(R.id.movieTitle).text = movie.name
        view.findViewById<TextView>(R.id.movieYear).text = movie.year
        view.findViewById<TextView>(R.id.description).text = movie.description
        var genres = ""
        movie.genres?.forEach {
            genres = genres + it.name + ", "
        }
        if (movie.genres != null && movie.genres.isNotEmpty()) {
            genres = genres.substring(0, genres.length - 2)
        }
        view.findViewById<TextView>(R.id.movieGenres).text = genres
        var countries = ""
        movie.countries?.forEach {
            countries = countries + it.name + ", "
        }
        if (movie.countries != null && movie.countries.isNotEmpty()) {
            countries = countries.substring(0, countries.length - 2)
        }
        view.findViewById<TextView>(R.id.movieCountries).text = countries
        view.findViewById<TextView>(R.id.rating).text = movie.rating?.kp.toString()
        val moviePosterView = view.findViewById<ImageView>(R.id.moviePoster)
        Glide
            .with(moviePosterView.context)
            .load(movie.poster?.url)
            .into(moviePosterView)
    }
}