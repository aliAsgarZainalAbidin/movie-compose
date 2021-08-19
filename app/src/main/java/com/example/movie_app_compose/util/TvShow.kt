package com.example.movie_app_compose.util

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

abstract class TvShow {
    abstract var voteAverage : Float?
    abstract var backdropPath: String?
    abstract var firstAirDate: String?
    abstract var posterPath: String?
    abstract var name: String?
    abstract var genres: List<Int>
    abstract var language: String?
    abstract var overview: String?
    abstract var popularity: Double?
    abstract var id: Int?
}
