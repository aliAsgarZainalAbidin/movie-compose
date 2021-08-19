package com.example.movie_app_compose.util

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


abstract class Movie {
    abstract var releaseDate: String?
    abstract var adult: Boolean?
    abstract var backdropPath: String?
    abstract var genreIds: List<Int>?
    abstract var voteCounts: Int?
    abstract var originalLanguage: String?
    abstract var originalTitle: String?
    abstract var posterPath: String
    abstract var video: Boolean?
    abstract var id: Int?
    abstract var voteAvarage: Float?
    abstract var title: String?
    abstract var overview: String?
    abstract var popularity: Double?
    abstract var mediaType: String?
}

//    (
//    @field:SerializedName("release_date")
//    open abstract var releaseDate: String? = "",
//
//    @field:SerializedName("adult")
//    open abstract var adult: Boolean? = false,
//
//    @field:SerializedName("backdrop_path")
//    open abstract var backdropPath: String? = "",
//
//    @field:SerializedName("genre_ids")
//    open abstract var genreIds: List<Int>? = listOf(),
//
//    @field:SerializedName("vote_count")
//    open abstract var voteCounts: Int? = -1,
//
//    @field:SerializedName("original_language")
//    open abstract var originalLanguage: String? = "",
//
//    @field:SerializedName("original_title")
//    open abstract var originalTitle: String? = "",
//
//    @field:SerializedName("poster_path")
//    open abstract var posterPath: String? = "",
//
//    @field:SerializedName("video")
//    open abstract var video: Boolean? = false,
//
//    @field:SerializedName("id")
//    open abstract var id: Int? = -1,
//
//    @field:SerializedName("vote_average")
//    open abstract var voteAabstract varage: Double? = 0.0,
//
//    @field:SerializedName("title")
//    open abstract var title: String? = "",
//
//    @field:SerializedName("overview")
//    open abstract var overview: String? = "",
//
//    @field:SerializedName("popularity")
//    open abstract var popularity: Double? = 0.0,
//
//    @field:SerializedName("media_type")
//    open abstract var mediaType: String? = ""
//)