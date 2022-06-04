package com.example.androidschool.data.network

import com.example.androidschool.BuildConfig

class NetworkMoviesService(
    private val movieApi: RetrofitServiceApi
) {

    suspend fun getMovie(movieId: Long) = movieApi.getMovie(movieId, BuildConfig.API_KEY)

    suspend fun getMovies(query: String) = movieApi.getMovies(query, BuildConfig.API_KEY)?.docs

    suspend fun getMoviesByYear(year: Int) = movieApi.getMoviesByYear(year, BuildConfig.API_KEY)
}