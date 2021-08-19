package com.example.movie_app_compose.util

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


abstract class Movie{
    abstract var releaseDate: String?
    abstract var voteAverage: Float?
    abstract var title: String?
    abstract var backdropPath: String?
    abstract var posterPath: String?
    abstract var adult: Boolean?
    abstract var genreIds: List<Int>?
    abstract var voteCounts: Int?
    abstract var originalLanguage: String?
    abstract var originalTitle: String?
    abstract var video: Boolean?
    abstract var id: Int?
    abstract var overview: String?
    abstract var popularity: Double?
    abstract var mediaType: String?
}