package com.example.androidschool.data.network

import com.example.androidschool.model.dto.Movie
import com.example.androidschool.model.dto.MoviesList
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServiceApi {
    @GET("/movie?field=id")
    suspend fun getMovie(@Query("search") movieId: Long, @Query("token") token: String): Movie?

    @GET("/movie?field=name&isStrict=false")
    suspend fun getMovies(
        @Query("search") movieName: String,
        @Query("token") token: String
    ): MoviesList?

    @GET("/movie?field=year&sortField=rating.kp&sortType=-1")
    suspend fun getMoviesByYear(
        @Query("search") year: Int,
        @Query("token") token: String
    ): MoviesList?
}