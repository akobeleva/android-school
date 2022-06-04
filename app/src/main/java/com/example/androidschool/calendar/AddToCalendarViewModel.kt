package com.example.androidschool.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidschool.data.MoviesService
import kotlinx.coroutines.launch

class AddToCalendarViewModel(application: Application) : AndroidViewModel(application) {
    private var scheduledMovie: MutableLiveData<Pair<Long, Long>> = MutableLiveData()
    private var moviesService: MoviesService =
        MoviesService.getInstance(application.applicationContext)

    fun setScheduledMovie(scheduledMovie: Pair<Long, Long>) {
        this.scheduledMovie.value = scheduledMovie
    }

    fun addScheduledMovie() {
        viewModelScope.launch {
            moviesService.addScheduledMovie(
                scheduledMovie.value!!.first,
                scheduledMovie.value!!.second
            )
        }
    }
}