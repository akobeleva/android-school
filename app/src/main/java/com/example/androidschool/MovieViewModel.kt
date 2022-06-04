package com.example.androidschool

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.androidschool.data.MoviesService
import com.example.androidschool.model.dto.Movie
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    var movie: MutableLiveData<Movie?> = MutableLiveData()
    private var moviesService: MoviesService =
        MoviesService.getInstance(application.applicationContext)

    fun getMovieById(movieId: Long) {
        MainScope().launch {
            val result = moviesService.getMovieById(movieId)
            movie.postValue(result)
        }
    }
}