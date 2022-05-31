package com.example.androidschool.data.network

import com.example.androidschool.model.dto.Movie
import com.example.androidschool.model.dto.MoviesList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServiceApi {
    @GET("/movie?field=id")
    fun getMovie(@Query("search") movieId: Long, @Query("token") token: String): Call<Movie>

    @GET("/movie?field=name&isStrict=false")
    fun getMovies(
        @Query("search") movieName: String,
        @Query("token") token: String
    ): Call<MoviesList>

    @GET("/movie?field=year&sortField=rating.kp&sortType=-1")
    fun getMoviesByYear(@Query("search") year: Int, @Query("token") token: String): Call<MoviesList>
}