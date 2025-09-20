package com.bc.tvappvlc.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {

    // ⚠️ URL base de tu servidor en Replit (IMPORTANTE: terminar con "/")
    private const val BASE_URL =
        "https://0880391e-8ff4-4235-8a95-6ee1e2eb1c4a-00-14tfrocqhbp3s.picard.replit.dev/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .build()
            .create(ApiService::class.java)
    }
}