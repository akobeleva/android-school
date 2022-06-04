package com.example.androidschool.data

import android.content.Context
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

    suspend fun getMovieById(movieId: Long): Movie? {
        return movieDao.getMovieById(movieId)
            ?.takeUnless { it.genres.isNullOrEmpty() || it.countries.isNullOrEmpty() }
            ?.let { movieConverter.entityToMovie(it) }
            ?: networkMoviesService.getMovie(movieId)
                ?.let {
                    movieDao.insertMovie(movieConverter.movieToEntity(it))
                    it
                }
    }

    suspend fun searchMovies(query: String) = networkMoviesService.getMovies(query)

    suspend fun getStartMovies() = movieDao.getActiveMovies()
        .takeIf { it.isNotEmpty() }
        ?.map { movieConverter.entityToMovie(it) }
        ?: listOf()

    suspend fun getNewMovies(): List<Movie> {
        val year = (START_YEAR..END_YEAR).random()

        return movieDao.getMoviesByYear(year.toString()).takeIf { it.isNotEmpty() }
            ?.map { movieConverter.entityToMovie(it) }
            ?.toList()
            ?: networkMoviesService.getMoviesByYear(year).docs.takeIf { it.isNotEmpty() }
                ?.let { movies ->
                    movieDao.updateNotActiveMovies()
                    movieDao.insertAll(movies.map { movie ->
                        movieConverter.movieToEntity(movie, true)
                    })
                    movies
                } ?: listOf()
    }

    suspend fun addScheduledMovie(date: Long, movieId: Long) =
        scheduledMovieDao.insertScheduledMovie(ScheduledMovieEntity(date, movieId))

    suspend fun getScheduledMovies(): List<Any> {
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
        return linkedSet.toList()
    }
}
