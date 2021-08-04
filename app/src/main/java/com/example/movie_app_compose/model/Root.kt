package com.example.movie_app_compose.model

data class Root(
    var page : Int = 0,
    var results: List<Result>,
    var total_pages : Int = 0,
    var total_results : Int = 0
)
