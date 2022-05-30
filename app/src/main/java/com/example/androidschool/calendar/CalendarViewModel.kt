package com.example.androidschool.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.androidschool.data.MoviesService

class CalendarViewModel(application: Application) : AndroidViewModel(application) {
    var scheduledMovies: MutableLiveData<List<Any>> = MutableLiveData()
    private var moviesService: MoviesService =
        MoviesService.getInstance(application.applicationContext)

    fun getScheduledMovies() {
        moviesService.getScheduledMovies(scheduledMovies)
    }
}