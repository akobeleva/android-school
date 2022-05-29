package com.example.androidschool

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.androidschool.model.dto.Movie

class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    var movies: MutableLiveData<List<Movie>> = MutableLiveData()
    private var moviesService : MoviesService = MoviesService.getInstance(application.applicationContext)

    fun setMovies(moviesList: List<Movie>) {
        movies.value = moviesList
    }

    fun searchMovies(query: String) {
        moviesService.searchMovies(query, movies)
    }

    fun getStartMovies() {
        moviesService.getStartMovies(movies)
    }

    fun getNewMovies() {
        moviesService.getNewMovies(movies)
    }
}