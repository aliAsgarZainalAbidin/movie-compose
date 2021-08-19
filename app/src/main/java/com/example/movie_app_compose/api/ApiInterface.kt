package com.example.movie_app_compose.api

import com.example.movie_app_compose.data.entity.People
import com.example.movie_app_compose.model.RequestWrapper
import com.example.movie_app_compose.model.Root
import com.example.movie_app_compose.data.entity.OnTheAir
import com.example.movie_app_compose.data.entity.Playing
import com.example.movie_app_compose.data.entity.Trending
import com.example.movie_app_compose.util.Movie
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
        language : String = "en-US",
        @Query("page")
        page : String = "1"
    ) : Call<Root<People>>

    @GET("trending/movie/week")
    fun getTrendingMovies(
        @Query("api_key")
        apiKey: String = ""
    ) : Call<Root<Trending>>

    @GET("tv/on_the_air")
    fun getOnTheAir(
        @Query("api_key")
        apiKey: String = ""
    ) : Call<Root<OnTheAir>>

    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("api_key")
        apiKey: String = ""
    ) : Call<Root<Playing>>
}