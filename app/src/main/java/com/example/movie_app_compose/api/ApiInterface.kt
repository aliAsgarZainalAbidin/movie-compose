package com.example.movie_app_compose.api

import com.example.movie_app_compose.data.entity.*
import com.example.movie_app_compose.model.Detail
import com.example.movie_app_compose.model.RequestWrapper
import com.example.movie_app_compose.model.Root
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("authentication/token/new")
    fun createRequestToken(
        @Query("api_key")
        apiKey: String = ""
    ): Call<RequestWrapper>

    @GET("authentication/session/new")
    fun createSessionId(
        @Query("api_key")
        apiKey: String = "",

        @Query("request_token")
        requestToken: String = ""
    ): Call<RequestWrapper>

    @GET("person/popular")
    fun getPopularPerson(
        @Query("api_key")
        apiKey: String = "",
        @Query("language")
        language: String = "en-US",
        @Query("page")
        page: String = "1"
    ): Call<Root<People>>

    @GET("trending/movie/week")
    fun getTrendingMovies(
        @Query("api_key")
        apiKey: String = ""
    ): Call<Root<Trending>>

    @GET("tv/on_the_air")
    fun getOnTheAir(
        @Query("api_key")
        apiKey: String = "",
        @Query("page")
        page: String = "1"
    ): Call<Root<OnTheAir>>

    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("api_key")
        apiKey: String = ""
    ): Call<Root<Playing>>

    @GET("movie/upcoming")
    fun getUpcoming(
        @Query("api_key")
        apiKey: String = ""
    ): Call<Root<Upcoming>>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key")
        apiKey: String = ""
    ): Call<Root<PopularMovies>>

    @GET("tv/airing_today")
    fun getAiringToday(
        @Query("api_key")
        apiKey: String = ""
    ): Call<Root<AiringToday>>

    @GET("tv/popular")
    fun getPopularTvShow(
        @Query("api_key")
        apiKey: String = ""
    ): Call<Root<PopularTv>>

    @GET("{type}/{id}")
    fun getDetail(
        @Path("type")
        type: String = "",
        @Path("id")
        id: String = "",
        @Query("api_key")
        apiKey: String = ""
    ): Call<Detail>
}