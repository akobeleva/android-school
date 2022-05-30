package com.example.androidschool.data.network

import com.example.androidschool.model.dto.Movie
import com.example.androidschool.model.dto.MoviesList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServiceApi {
    @GET("/movie?field=id&token=RXBRMCW-TZQ4YGV-M59351K-GBC3ZTF")
    fun getMovie(@Query("search") movieId: Long): Call<Movie>

    @GET("/movie?field=name&token=RXBRMCW-TZQ4YGV-M59351K-GBC3ZTF&isStrict=false")
    fun getMovies(@Query("search") movieName: String): Call<MoviesList>

    @GET("/movie?field=year&sortField=rating.kp&sortType=-1&token=RXBRMCW-TZQ4YGV-M59351K-GBC3ZTF")
    fun getMoviesByYear(@Query("search") year: Int): Call<MoviesList>
}