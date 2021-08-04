package com.example.movie_app_compose.model

data class KnownFor(
    var backdrop_path: String,
    var first_air_date: String,
    var genre_ids: List<Int>,
    var id : Int = 0,
    var media_type: String,
    var name: String,
    var origin_country: List<String>,
    var original_language: String,
    var original_name: String,
    var overview: String,
    var poster_path: String,
    var vote_average : Float,
    var vote_count : Int,
    var adult :Boolean,
    var original_title: String,
    var release_date: String,
    var title: String,
    var video: Boolean
)
