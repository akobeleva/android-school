package com.example.androidschool.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.androidschool.data.db.Database
import com.example.androidschool.data.db.MovieDao
import com.example.androidschool.data.db.ScheduledMovieDao
import com.example.androidschool.data.network.NetworkService
import com.example.androidschool.data.network.RetrofitServiceApi
import com.example.androidschool.model.MovieConverter
import com.example.androidschool.model.dto.Movie
import com.example.androidschool.model.dto.MoviesList
import com.example.androidschool.model.entity.ScheduledMovieEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

object MoviesService {
    private val movieConverter = MovieConverter()
    private var movieServiceInstance: MoviesService? = null
    private var movieDao: MovieDao? = null
    private var scheduledMovieDao: ScheduledMovieDao? = null
    private var retrofitServiceApi: RetrofitServiceApi? = null

    fun getInstance(context: Context): MoviesService {
        if (movieServiceInstance == null) {
            movieServiceInstance = MoviesService
            movieDao = Database.getDatabase(context).movieDao()
            scheduledMovieDao = Database.getDatabase(context).scheduledMovieDao()
            retrofitServiceApi = NetworkService.getInstance()?.getRetrofitServiceApi()
        }
        return movieServiceInstance as MoviesService
    }

    private fun getMovieByIdFromDb(movieId: Long) = movieDao?.getMovieById(movieId)

    fun getMovieById(movieId: Long, movieLiveData: MutableLiveData<Movie>) {
        val dbMovie = getMovieByIdFromDb(movieId)
        if (dbMovie != null && !(dbMovie.genres.isNullOrEmpty() || dbMovie.countries.isNullOrEmpty())) {
            movieLiveData.value = movieConverter.entityToMovie(dbMovie)
        } else {
            retrofitServiceApi
                ?.getMovie(movieId)
                ?.enqueue(object : Callback<Movie> {
                    override fun onResponse(call: Call<Movie?>, response: Response<Movie?>) {
                        movieDao?.insertMovie(movieConverter.movieToEntity(response.body()!!))
                        movieLiveData.value = response.body()
                    }

                    override fun onFailure(call: Call<Movie>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }
    }

    fun searchMovies(query: String, movies: MutableLiveData<List<Movie>>) {
        retrofitServiceApi
            ?.getMovies(query)
            ?.enqueue(object : Callback<MoviesList> {
                override fun onResponse(
                    call: Call<MoviesList>,
                    response: Response<MoviesList>
                ) {
                    response.body()?.let {
                        movies.value = it.docs
                    }
                }

                override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    fun getStartMovies(movies: MutableLiveData<List<Movie>>) {
        val dbMovies = movieDao?.getActiveMovies()
        if (dbMovies?.isEmpty()!!) {
            getNewMovies(movies)
        } else {
            movies.value = dbMovies.map { MovieConverter().entityToMovie(it) }
        }
    }

    fun getNewMovies(movies: MutableLiveData<List<Movie>>) {
        val year = (2000..2022).random()

        val dbMovies = movieDao?.getMoviesByYear(year.toString())
        if (!dbMovies?.isEmpty()!!) {
            movies.value = dbMovies.map { MovieConverter().entityToMovie(it) }
        } else {
            retrofitServiceApi
                ?.getMoviesByYear(year)
                ?.enqueue(object : Callback<MoviesList> {
                    override fun onResponse(
                        call: Call<MoviesList>,
                        response: Response<MoviesList>
                    ) {
                        response.body()?.let {
                            movies.value = it.docs
                            movieDao?.updateNotActiveMovies()
                            movieDao?.insertAll(it.docs.map { movie ->
                                MovieConverter().movieToEntity(movie, true)
                            })
                        }
                    }

                    override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                        movies.value = emptyList()
                        t.printStackTrace()
                    }
                })
        }
    }

    fun addScheduledMovie(date: Long, movieId: Long) =
        scheduledMovieDao?.insertScheduledMovie(ScheduledMovieEntity(date, movieId))

    fun getScheduledMovies(scheduledMoviesLiveData: MutableLiveData<List<Any>>) {
        val entitiesMap = scheduledMovieDao?.getScheduledMovies()
        val scheduledMovies = entitiesMap?.keys?.map { movieConverter.entityToScheduledMovie(it) }
            ?.sortedBy { it.date }
        val moviesList = entitiesMap?.values?.map {
            it.map { movieEntity ->
                movieConverter.entityToMovie(movieEntity)
            }
        }?.flatten()
        val linkedSet = LinkedHashSet<Any>()
        scheduledMovies?.forEach {
            val millisInDay = (60 * 60 * 24 * 1000).toLong()
            val dateOnly = it.date / millisInDay * millisInDay
            linkedSet.add(Date(dateOnly))
            val movie = moviesList?.first { movie -> movie.id == it.movieId }!!
            linkedSet.add(movie)
        }
        scheduledMoviesLiveData.value = linkedSet.toList()
    }
}
