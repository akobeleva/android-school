package com.example.androidschool.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private var networkServiceInstance: NetworkService? = null
    private const val BASE_URL = "https://api.kinopoisk.dev"
    private var retrofit: Retrofit? = null

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    fun getInstance(): NetworkService? {
        if (networkServiceInstance == null) {
            networkServiceInstance = NetworkService
        }
        return networkServiceInstance
    }

    fun getRetrofitServiceApi(): RetrofitServiceApi? {
        return retrofit?.create(RetrofitServiceApi::class.java)
    }
}