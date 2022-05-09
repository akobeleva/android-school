package com.example.androidschool.network

import com.example.androidschool.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServiceApi {
    @GET("/movie?field=id&token=ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06")
    fun getMovie(@Query("search") movieId: Int): Call<Movie>
}