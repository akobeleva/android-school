package com.example.androidschool

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.androidschool.data.MoviesService
import com.example.androidschool.model.dto.Movie

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    var movie: MutableLiveData<Movie> = MutableLiveData()
    private var moviesService : MoviesService = MoviesService.getInstance(application.applicationContext)

    fun getMovieById(movieId : Long) {
        moviesService.getMovieById(movieId, movie)
    }
}