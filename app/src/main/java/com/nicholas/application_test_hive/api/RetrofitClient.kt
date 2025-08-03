package com.nicholas.application_test_hive.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // IMPORTANT: Replace this with your actual URL from mockapi.io
    private const val BASE_URL = "https://688cadbbcd9d22dda5ce0f0f.mockapi.io/api/v1/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val habitService: HabitService by lazy {
        retrofit.create(HabitService::class.java)
    }
}