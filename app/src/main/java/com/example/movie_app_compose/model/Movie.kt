package com.example.movie_app_compose.model

data class Movie(
    var releaseDate : String,
    var adult : Boolean,
    var backdropPath : String,
    var genreIds : List<Int>,
    var voteCounts : Int,
    var originalLanguage : String,
    var originalTitle : String,
    var posterPath : String,
    var video : Boolean,
    var id : Int,
    var voteAvarage : Double,
    var title : String,
    var overview : String,
    var popularity : Double,
    var mediaType : String
)
