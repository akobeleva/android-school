package com.example.androidschool

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidschool.model.Movie

class MoviesViewModel : ViewModel() {
    var movies: MutableLiveData<List<Movie>> = MutableLiveData()

    fun setMovies(moviesList: List<Movie>) {
        movies.value = moviesList
    }
}