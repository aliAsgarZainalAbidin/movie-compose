package com.example.movie_app_compose.util

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

abstract class TvShow : BaseModel() {
    abstract override var voteAverage : Float?
    abstract override var backdropPath: String?
    abstract override var firstAirDate: String?
    abstract override var posterPath: String?
    abstract override var name: String?
    abstract var genres: List<Int>
    abstract var language: String?
    abstract var overview: String?
    abstract var popularity: Double?
    abstract var id: Int?
}
