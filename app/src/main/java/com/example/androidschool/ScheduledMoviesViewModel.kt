package com.example.androidschool

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.androidschool.model.dto.Movie

class ScheduledMoviesViewModel(application: Application) : AndroidViewModel(application) {
    var scheduledMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    private var moviesService: MoviesService =
        MoviesService.getInstance(application.applicationContext)

    fun getScheduledMovies() {
        moviesService.getNewMovies(scheduledMovies)
    }
}