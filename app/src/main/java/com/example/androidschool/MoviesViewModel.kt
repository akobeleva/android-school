package com.example.androidschool

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidschool.data.MoviesService
import com.example.androidschool.model.dto.Movie
import kotlinx.coroutines.launch

class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    var movies: MutableLiveData<List<Movie>> = MutableLiveData()
    private var moviesService: MoviesService =
        MoviesService.getInstance(application.applicationContext)

    fun searchMovies(query: String) {
        viewModelScope.launch {
            val result = moviesService.searchMovies(query)
            movies.postValue(result.docs)
        }
    }

    fun getStartMovies() {
        viewModelScope.launch {
            val result = moviesService.getStartMovies()
            movies.postValue(result)
        }
    }

    fun getNewMovies() {
        viewModelScope.launch {
            val result = moviesService.getNewMovies()
            movies.postValue(result)
        }
    }
}