package com.example.movie_app_compose.api

import com.example.movie_app_compose.model.RequestWrapper
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET

interface ApiInterface {
    @GET("authentication/token/new")
    fun createRequestToken(
        @Field("api_key")
        apiKey: String = ""
    ): Call<RequestWrapper>

    @GET("authentication/session/new")
    fun createSessionId(
        @Field("api_key")
        apiKey: String = "",

        @Field("request_token")
        requestToken: String = ""
    ): Call<RequestWrapper>
}