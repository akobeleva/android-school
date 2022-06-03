package com.example.androidschool.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {
    companion object {
        private val RETROFIT_API_INSTANCE: RetrofitServiceApi by lazy {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)

            return@lazy Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
                .create(RetrofitServiceApi::class.java)
        }

        fun getRetrofitServiceApi() = RETROFIT_API_INSTANCE
        private const val BASE_URL = "https://api.kinopoisk.dev"
    }
}