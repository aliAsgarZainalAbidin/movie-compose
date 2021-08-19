package com.example.movie_app_compose.util

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


abstract class Movie : BaseModel() {
    abstract override var releaseDate: String?
    abstract override var voteAverage: Float?
    abstract override var title: String?
    abstract override var backdropPath: String?
    abstract override var posterPath: String?
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