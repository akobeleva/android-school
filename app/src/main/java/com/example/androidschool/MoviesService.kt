package com.example.androidschool

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.androidschool.db.Database
import com.example.androidschool.db.MovieDao
import com.example.androidschool.model.dto.Movie
import com.example.androidschool.model.dto.MoviesList
import com.example.androidschool.network.NetworkService
import com.example.androidschool.network.RetrofitServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MoviesService {
    private var movieServiceInstance: MoviesService? = null
    private var movieDao: MovieDao? = null
    private var retrofitServiceApi: RetrofitServiceApi? = null

    fun getInstance(context: Context): MoviesService {
        if (movieServiceInstance == null) {
            movieServiceInstance = MoviesService
            movieDao = Database.getDatabase(context).movieDao()
            retrofitServiceApi = NetworkService.getInstance()?.getRetrofitServiceApi()
        }
        return movieServiceInstance as MoviesService
    }

    fun getMovieById(movieId: Long, movie: MutableLiveData<Movie>){
        retrofitServiceApi
            ?.getMovie(movieId)
            ?.enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie?>, response: Response<Movie?>) {
                    movie.value = response.body()
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    t.printStackTrace()
                }
            })
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
                                MovieConverter().movieToEntity(movie)
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
}
