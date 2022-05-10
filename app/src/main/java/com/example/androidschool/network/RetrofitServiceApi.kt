package com.example.androidschool.network

import com.example.androidschool.model.Movie
import com.example.androidschool.model.MoviesList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServiceApi {
    @GET("/movie?field=id&token=ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06")
    fun getMovie(@Query("search") movieId: Int): Call<Movie>

    @GET("/movie?field=name&token=ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06&isStrict=false")
    fun getMovies(@Query("search") movieName: String): Call<MoviesList>
}