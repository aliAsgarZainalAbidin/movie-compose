package com.example.movie_app_compose.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class TvShow(
    @ColumnInfo(name = "backdrop_path")
    @field:SerializedName("backdrop_path")
    var backdropPath: String? = "",

    @ColumnInfo(name = "first_air_date")
    @field:SerializedName("first_air_date")
    var firstAirDate: String? = "",

    @ColumnInfo(name = "genres")
    @field:SerializedName("genres")
    var genres: List<Genre> = listOf(),

    @ColumnInfo(name = "original_language")
    @field:SerializedName("original_language")
    var language: String? = "",

    @ColumnInfo(name = "overview")
    @field:SerializedName("overview")
    var overview: String? = "",

    @ColumnInfo(name = "popularity")
    @field:SerializedName("popularity")
    var popularity: Double? = 0.0,

    @ColumnInfo(name = "poster_path")
    @field:SerializedName("poster_path")
    var posterPath: String? = "",

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    var name: String? = "",

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    var id: Int? = -1
)

//"backdrop_path":"/g0W42JhKllNj7v0vOOpZCwyrRwN.jpg",
//"created_by":[
//
//],
//"episode_run_time":[
//
//],
//"first_air_date":"2020-02-29",
//"genres":[
//{
//    "id":18,
//    "name":"Drama"
//},
//{
//    "id":10765,
//    "name":"Sci-Fi & Fantasy"
//}
//],
//"homepage":"",
//"id":131233,
//"in_production":true,
//"languages":[
//"ko"
//],
//"last_air_date":"2020-02-29",
//"last_episode_to_air":{
//    "air_date":"2020-02-29",
//    "episode_number":1,
//    "id":3132043,
//    "name":"",
//    "overview":"A late-night cafe runs from 12 midnight to sunrise. Some unique secrets are hidden in there. Jae Yeong, upon seeing a crappy job advertisement, visited the cafe and meets his unexpected fate.",
//    "production_code":"",
//    "season_number":1,
//    "still_path":null,
//    "vote_average":0.0,
//    "vote_count":0
//},
//"name":"Cafe Midnight",
//"next_episode_to_air":null,
//"networks":[
//{
//    "name":"MBC",
//    "id":97,
//    "logo_path":"/pOSCKaZhndUFYtxHXjQOV6xJi1s.png",
//    "origin_country":"KR"
//}
//],
//"number_of_episodes":1,
//"number_of_seasons":3,
//"origin_country":[
//"KR"
//],
//"original_language":"en",
//"original_name":"Cafe Midnight",
//"overview":"A late-night cafe runs from 12 midnight to sunrise. Some unique secrets are hidden in there. Jae Yeong, upon seeing a crappy job advertisement, visited the cafe and meets his unexpected fate.",
//"popularity":0.6,
//"poster_path":"/1Epg5WL60jgquRz1N7EmSP9LjCQ.jpg",
//"production_companies":[
//{
//    "id":136349,
//    "logo_path":"/cFFJ5oRzrpzuWjd1QV1VJ4N7rKT.png",
//    "name":"MBC C&I",
//    "origin_country":"KR"
//}
//],
//"production_countries":[
//{
//    "iso_3166_1":"KR",
//    "name":"South Korea"
//}
//],
//"seasons":[
//{
//    "air_date":"2020-02-28",
//    "episode_count":1,
//    "id":205965,
//    "name":"Cafe Midnight",
//    "overview":"A late-night cafe runs from 12 midnight to sunrise. Some unique secrets are hidden in there. Jae Yeong, upon seeing a crappy job advertisement, visited the cafe and meets his unexpected fate.",
//    "poster_path":null,
//    "season_number":1
//}
//],
//"spoken_languages":[
//{
//    "english_name":"Korean",
//    "iso_639_1":"ko",
//    "name":"한국어/조선말"
//}
//],
//"status":"Returning Series",
//"tagline":"",
//"type":"Scripted",
//"vote_average":0.0,
//"vote_count":0

