package com.example.androidschool

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide


class MovieFragment : Fragment(R.layout.movie_layout) {
    private val viewModel: MovieViewModel by viewModels()

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
        subscribeToViewModel(view)

        val movieId = requireArguments().getLong("movieId")
        viewModel.getMovieById(movieId)
        val addToCalendarButton = view.findViewById<ImageButton>(R.id.addToCalendarButton)
        addToCalendarButton.setOnClickListener {
            val addToCalendarFragment = AddToCalendarFragment()
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, addToCalendarFragment)
                .addToBackStack(null)
                .commit()
        }
        val backPressButton = view.findViewById<ImageView>(R.id.backPressButton)
        backPressButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun showMovie(view: View) {
        val movie = viewModel.movie.value
        view.findViewById<TextView>(R.id.movieTitle).text = movie!!.name
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

    private fun subscribeToViewModel(view: View) {
        viewModel.movie.observe(viewLifecycleOwner) {
            if (it != null) {
                showMovie(view)
            } else {
                val text = "Проверьте соединение и попробуйте еще раз"
                val duration = Toast.LENGTH_LONG

                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
        }
    }
}