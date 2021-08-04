package com.example.movie_app_compose.model

data class People(
    var adult: Boolean,
    var gender: Int,
    var id: Int,
    var known_for: List<KnownFor>,
    var known_for_department: String,
    var name: String,
    var popularity: Float,
    var profile_path: String
)

