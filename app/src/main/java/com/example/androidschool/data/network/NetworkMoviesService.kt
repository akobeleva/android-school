package com.example.androidschool.data.network

import com.example.androidschool.BuildConfig
import com.example.androidschool.model.dto.Movie
import com.example.androidschool.model.dto.MoviesList
import java.io.IOException

class NetworkMoviesService(
    private val movieApi: RetrofitServiceApi
) {

    suspend fun getMovie(movieId: Long): Movie? {
        return try {
            movieApi.getMovie(movieId, BuildConfig.API_KEY)
        } catch (e: IOException) {
            null
        }
    }

    suspend fun getMovies(query: String): MoviesList {
        return try {
            movieApi.getMovies(query, BuildConfig.API_KEY)
        } catch (e: IOException) {
            MoviesList(listOf())
        }
    }

    suspend fun getMoviesByYear(year: Int): MoviesList {
        return try {
            movieApi.getMoviesByYear(year, BuildConfig.API_KEY)
        } catch (e: IOException) {
            MoviesList(listOf())
        }
    }
}