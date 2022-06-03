package com.example.androidschool.data.network

import com.example.androidschool.BuildConfig
import com.example.androidschool.model.dto.Movie
import com.example.androidschool.model.dto.MoviesList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkMoviesService(
    private val movieApi: RetrofitServiceApi
) {

    fun getMovie(movieId: Long, onResultCallback: (movie: Movie) -> Unit) {
        movieApi.getMovie(movieId, BuildConfig.API_KEY)
            .enqueue(
                object : Callback<Movie> {
                    override fun onResponse(call: Call<Movie?>, response: Response<Movie?>) {
                        onResultCallback.invoke(response.body()!!)
                    }

                    override fun onFailure(call: Call<Movie>, t: Throwable) {
                        t.printStackTrace()
                    }
                }
            )
    }

    fun getMovies(query: String, onResultCallback: (moviesList: MoviesList) -> Unit) {
        movieApi.getMovies(query, BuildConfig.API_KEY)
            .enqueue(
                object : Callback<MoviesList> {
                    override fun onResponse(
                        call: Call<MoviesList>,
                        response: Response<MoviesList>
                    ) {
                        response.body()?.let { onResultCallback.invoke(MoviesList(it.docs)) }
                    }

                    override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                        t.printStackTrace()
                    }
                }
            )
    }

    fun getMoviesByYear(year: Int, onResultCallback: (moviesList: MoviesList) -> Unit) {
        movieApi.getMoviesByYear(year, BuildConfig.API_KEY)
            .enqueue(
                object : Callback<MoviesList> {
                    override fun onResponse(
                        call: Call<MoviesList>,
                        response: Response<MoviesList>
                    ) {
                        response.body()?.let { onResultCallback.invoke(it) }
                    }

                    override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                        onResultCallback.invoke(MoviesList(emptyList()))
                        t.printStackTrace()
                    }
                }
            )
    }
}