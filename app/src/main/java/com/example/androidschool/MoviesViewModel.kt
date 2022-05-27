package com.example.androidschool

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.androidschool.db.Database
import com.example.androidschool.db.MovieDao
import com.example.androidschool.model.dto.Movie
import com.example.androidschool.model.dto.MoviesList
import com.example.androidschool.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    var movies: MutableLiveData<List<Movie>> = MutableLiveData()
    private var movieDao: MovieDao = Database.getDatabase(application.applicationContext).movieDao()

    fun setMovies(moviesList: List<Movie>) {
        movies.value = moviesList
    }

    fun searchMovies(query: String) {
        NetworkService.getInstance()
            ?.getRetrofitServiceApi()
            ?.getMovies(query)
            ?.enqueue(object : Callback<MoviesList> {
                override fun onResponse(
                    call: Call<MoviesList>,
                    response: Response<MoviesList>
                ) {
                    response.body()?.let {
                        print(it.docs)
                        setMovies(it.docs)
                    }
                }

                override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                    setMovies(emptyList())
                }
            })
    }

    fun getStartMovies() {
        val dbMovies = movieDao.getAllMovies()
        if (dbMovies?.isEmpty()!!) {
            getNewMovies()
        } else {
            val movies = dbMovies.map { MovieConverter().entityToMovie(it) }
            setMovies(movies)
        }
    }

    fun getNewMovies() {
        val year = (2000..2022).random()
        getMoviesByYear(year)
    }

    private fun getMoviesByYear(year: Int) {
        NetworkService.getInstance()
            ?.getRetrofitServiceApi()
            ?.getMoviesByYear(year)
            ?.enqueue(object : Callback<MoviesList> {
                override fun onResponse(
                    call: Call<MoviesList>,
                    response: Response<MoviesList>
                ) {
                    response.body()?.let {
                        print(it.docs)
                        setMovies(it.docs)
                    }
                }

                override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                    setMovies(emptyList())
                }
            })
    }

    fun reloadDatabaseMovies() {
        movies.value?.let {
            movieDao.deleteMovies()
            movieDao.insertAll(it.map { movie ->
                MovieConverter().movieToEntity(movie)
            })
        }
    }
}