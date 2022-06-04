package com.example.androidschool.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidschool.data.MoviesService
import kotlinx.coroutines.launch

class CalendarViewModel(application: Application) : AndroidViewModel(application) {
    var scheduledMovies: MutableLiveData<List<Any>> = MutableLiveData()
    private var moviesService: MoviesService =
        MoviesService.getInstance(application.applicationContext)

    fun getScheduledMovies() {
        viewModelScope.launch {
            val result = moviesService.getScheduledMovies()
            scheduledMovies.postValue(result)
        }
    }
}