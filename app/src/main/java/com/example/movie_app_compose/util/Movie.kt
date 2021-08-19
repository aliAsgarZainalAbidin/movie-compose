package com.example.movie_app_compose.util

import com.google.gson.annotations.SerializedName


abstract class Movie {
    abstract val voteAvarage: Float?
    abstract val releaseDate: String?
    abstract val title: String?
    abstract val posterPath: String?
}

//    (
//    @field:SerializedName("release_date")
//    open var releaseDate: String? = "",
//
//    @field:SerializedName("adult")
//    open var adult: Boolean? = false,
//
//    @field:SerializedName("backdrop_path")
//    open var backdropPath: String? = "",
//
//    @field:SerializedName("genre_ids")
//    open var genreIds: List<Int>? = listOf(),
//
//    @field:SerializedName("vote_count")
//    open var voteCounts: Int? = -1,
//
//    @field:SerializedName("original_language")
//    open var originalLanguage: String? = "",
//
//    @field:SerializedName("original_title")
//    open var originalTitle: String? = "",
//
//    @field:SerializedName("poster_path")
//    open var posterPath: String? = "",
//
//    @field:SerializedName("video")
//    open var video: Boolean? = false,
//
//    @field:SerializedName("id")
//    open var id: Int? = -1,
//
//    @field:SerializedName("vote_average")
//    open var voteAvarage: Double? = 0.0,
//
//    @field:SerializedName("title")
//    open var title: String? = "",
//
//    @field:SerializedName("overview")
//    open var overview: String? = "",
//
//    @field:SerializedName("popularity")
//    open var popularity: Double? = 0.0,
//
//    @field:SerializedName("media_type")
//    open var mediaType: String? = ""
//)