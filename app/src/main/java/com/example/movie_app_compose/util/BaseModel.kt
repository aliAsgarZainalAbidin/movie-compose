package com.example.movie_app_compose.util

abstract class BaseModel {
    abstract var backdropPath: String?
    abstract var releaseDate: String?
    abstract var posterPath: String?
    abstract var voteAverage: Float?
    abstract var title: String?
    abstract var name: String?
    abstract var firstAirDate: String?
}