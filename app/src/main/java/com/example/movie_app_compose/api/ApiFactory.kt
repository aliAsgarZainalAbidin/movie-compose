package com.example.movie_app_compose.api

import com.example.movie_app_compose.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {
    fun create(timeOut: Long = 60): ApiInterface {
        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.readTimeout(timeOut, TimeUnit.SECONDS)
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original: Request = chain.request()
                val request: Request = original.newBuilder()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }

        val client = httpClient.build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()

        return retrofit.create(ApiInterface::class.java)
    }
}