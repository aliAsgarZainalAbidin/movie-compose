package com.example.movie_app_compose.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movie_app_compose.model.Genre
import com.example.movie_app_compose.util.Movie
import com.google.gson.annotations.SerializedName

@Entity
data class MyMovie(
    @ColumnInfo(name = "release_date")
    @field:SerializedName("release_date")
    var releaseDate: String? = "",

    @ColumnInfo(name = "is_saved")
    var isSaved: Boolean? = false,

    @ColumnInfo(name = "adult")
    @field:SerializedName("adult")
    var adult: Boolean? = false,

    @ColumnInfo(name = "backdrop_path")
    @field:SerializedName("backdrop_path")
    var backdropPath: String? = "",

    @ColumnInfo(name = "genre_ids")
    @field:SerializedName("genre_ids")
    var genreIds: List<Genre>? = listOf(),

    @ColumnInfo(name = "vote_count")
    @field:SerializedName("vote_count")
    var voteCounts: Int? = -1,

    @ColumnInfo(name = "original_language")
    @field:SerializedName("original_language")
    var originalLanguage: String? = "",

    @ColumnInfo(name = "original_title")
    @field:SerializedName("original_title")
    var originalTitle: String? = "",

    @ColumnInfo(name = "poster_path")
    @field:SerializedName("poster_path")
    var posterPath: String? = "",

    @ColumnInfo(name = "video")
    @field:SerializedName("video")
    var video: Boolean? = false,

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    var id: Int? = -1,

    @ColumnInfo(name = "vote_average")
    @field:SerializedName("vote_average")
    var voteAverage: Float? = 0.0f,

    @ColumnInfo(name = "title")
    @field:SerializedName("title")
    var title: String? = "",

    @ColumnInfo(name = "overview")
    @field:SerializedName("overview")
    var overview: String? = "",

    @ColumnInfo(name = "popularity")
    @field:SerializedName("popularity")
    var popularity: Double? = 0.0,

    @ColumnInfo(name = "mediaType")
    @field:SerializedName("media_type")
    var mediaType: String? = "",

    var type: String? = "",
    var language: String? = "",
)
