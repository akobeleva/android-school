package com.example.androidschool

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.androidschool.db.Database
import com.example.androidschool.db.MovieDao
import com.example.androidschool.model.dto.Movie
import com.example.androidschool.model.dto.MoviesList
import com.example.androidschool.network.NetworkService
import com.example.androidschool.network.RetrofitServiceApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.function.Consumer

object MoviesService {
    private var movieServiceInstance: MoviesService? = null
    private var movieDao: MovieDao? = null
    private var retrofitServiceApi: RetrofitServiceApi? = null

    fun getInstance(context: Context): MoviesService {
        if (movieServiceInstance == null) {
            movieServiceInstance = MoviesService
            movieDao = Database.getDatabase(context).movieDao()
//            retrofitServiceApi = NetworkService.getInstance()?.getRetrofitServiceApi()
        }
        return movieServiceInstance as MoviesService
    }

    fun getMovieById(movieId: Long): Movie? {
        var movie: Movie? = null
        NetworkService.getInstance()?.getRetrofitServiceApi()
            ?.getMovie(movieId)
            ?.enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie?>, response: Response<Movie?>) {
                    movie = response.body()
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        return movie
    }

    fun searchMovies(query: String): List<Movie> {
        var movies: List<Movie> = emptyList()
        NetworkService.getInstance()?.getRetrofitServiceApi()
            ?.getMovies(query)
            ?.enqueue(object : Callback<MoviesList> {
                override fun onResponse(
                    call: Call<MoviesList>,
                    response: Response<MoviesList>
                ) {
                    response.body()?.let {
                        movies = it.docs
                    }
                }

                override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        return movies
    }

    fun getStartMovies(): List<Movie> {
        val dbMovies = movieDao?.getAllMovies()
        var movies: List<Movie> =
            if (dbMovies?.isEmpty()!!) {
                return listOf()
                //getNewMovies(() -> )
            } else {
                dbMovies.map { MovieConverter().entityToMovie(it) }
            }
        return movies
    }

     suspend fun getNewMovies(): MutableLiveData<List<Movie>> {
        val year = (2000..2022).random()
        val retrofitApi = NetworkService.getInstance()?.getRetrofitServiceApi()
        return retrofitApi?.getMoviesByYear(year)?.docs!!

//            object : Callback<MoviesList> {
//                @RequiresApi(Build.VERSION_CODES.N)
//                override fun onResponse(
//                    call: Call<MoviesList>,
//                    response: Response<MoviesList>
//                ) {
//                    response.body()?.let { movies.value = it.docs }
//                }
//
//                override fun onFailure(call: Call<MoviesList>, t: Throwable) {
//                    t.printStackTrace()
//                }
//
//            }
//            )
    }

}