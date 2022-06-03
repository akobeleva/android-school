package com.example.androidschool.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.androidschool.data.db.Database
import com.example.androidschool.data.db.MovieDao
import com.example.androidschool.data.db.ScheduledMovieDao
import com.example.androidschool.data.network.NetworkMoviesService
import com.example.androidschool.data.network.NetworkService
import com.example.androidschool.model.MovieConverter
import com.example.androidschool.model.dto.Movie
import com.example.androidschool.model.entity.ScheduledMovieEntity
import java.util.Date

class MoviesService private constructor(context: Context) {
    private val movieConverter = MovieConverter()
    private val networkMoviesService = NetworkMoviesService(NetworkService.getRetrofitServiceApi())
    private val movieDao: MovieDao = Database.getDatabase(context).movieDao()
    private val scheduledMovieDao: ScheduledMovieDao =
        Database.getDatabase(context).scheduledMovieDao()

    companion object {
        private const val START_YEAR = 1990
        private const val END_YEAR = 2022
        private const val MILLISECONDS_IN_DAY = 60 * 60 * 24 * 1000L
        private var movieServiceInstance: MoviesService? = null
        fun getInstance(context: Context): MoviesService {
            if (movieServiceInstance == null) {
                movieServiceInstance = MoviesService(context)
            }
            return movieServiceInstance as MoviesService
        }
    }

    fun getMovieById(movieId: Long, movieLiveData: MutableLiveData<Movie>) {
        movieDao.getMovieById(movieId)?.let {
            if (!(it.genres.isNullOrEmpty() || it.countries.isNullOrEmpty())) {
                movieLiveData.value = movieConverter.entityToMovie(it)
            }
        } ?: networkMoviesService.getMovie(movieId) { resultMovie ->
            movieLiveData.value = resultMovie
            movieDao.insertMovie(movieConverter.movieToEntity(resultMovie))
        }
    }

    fun searchMovies(query: String, movies: MutableLiveData<List<Movie>>) {
        networkMoviesService.getMovies(query) { result ->
            movies.value = result.docs
        }
    }

    fun getStartMovies(movies: MutableLiveData<List<Movie>>) {
        val dbMovies = movieDao.getActiveMovies()
        if (dbMovies.isEmpty()) {
            getNewMovies(movies)
        } else {
            movies.value = dbMovies.map { movieConverter.entityToMovie(it) }
        }
    }

    fun getNewMovies(movies: MutableLiveData<List<Movie>>) {
        val year = (START_YEAR..END_YEAR).random()

        val dbMovies = movieDao.getMoviesByYear(year.toString())
        if (dbMovies.isNotEmpty()) {
            movies.value = dbMovies.map { movieConverter.entityToMovie(it) }
        } else {
            networkMoviesService.getMoviesByYear(year) { result ->
                movies.value = result.docs
                if (result.docs.isNotEmpty()) {
                    movies.value = result.docs
                    movieDao.updateNotActiveMovies()
                    movieDao.insertAll(result.docs.map { movie ->
                        movieConverter.movieToEntity(movie, true)
                    })
                }
            }
        }
    }

    fun addScheduledMovie(date: Long, movieId: Long) =
        scheduledMovieDao.insertScheduledMovie(ScheduledMovieEntity(date, movieId))

    fun getScheduledMovies(scheduledMoviesLiveData: MutableLiveData<List<Any>>) {
        val entitiesMap = scheduledMovieDao.getScheduledMovies()
        val scheduledMovies = entitiesMap.keys.map { movieConverter.entityToScheduledMovie(it) }
            .sortedBy { it.date }
        val moviesList = entitiesMap.values.map {
            it.map { movieEntity ->
                movieConverter.entityToMovie(movieEntity)
            }
        }.flatten()
        val linkedSet = LinkedHashSet<Any>()
        scheduledMovies.forEach {
            val dateOnly = it.date / MILLISECONDS_IN_DAY * MILLISECONDS_IN_DAY
            linkedSet.add(Date(dateOnly))
            val movie = moviesList.first { movie -> movie.id == it.movieId }
            linkedSet.add(movie)
        }
        scheduledMoviesLiveData.value = linkedSet.toList()
    }
}
