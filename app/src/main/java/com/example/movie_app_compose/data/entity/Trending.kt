package com.example.movie_app_compose.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movie_app_compose.util.Movie
import com.google.gson.annotations.SerializedName

@Entity
data class Trending(
    @ColumnInfo(name = "release_date")
    @field:SerializedName("release_date")
    override var releaseDate: String? = "",

    @ColumnInfo(name = "adult")
    @field:SerializedName("adult")
    override var adult: Boolean? = false,

    @ColumnInfo(name = "backdrop_path")
    @field:SerializedName("backdrop_path")
    override var backdropPath: String? = "",

    @ColumnInfo(name = "genre_ids")
    @field:SerializedName("genre_ids")
    override var genreIds: List<Int>? = listOf(),

    @ColumnInfo(name = "vote_count")
    @field:SerializedName("vote_count")
    override var voteCounts: Int? = -1,

    @ColumnInfo(name = "original_language")
    @field:SerializedName("original_language")
    override var originalLanguage: String? = "",

    @ColumnInfo(name = "original_title")
    @field:SerializedName("original_title")
    override var originalTitle: String? = "",

    @ColumnInfo(name = "poster_path")
    @field:SerializedName("poster_path")
    override var posterPath: String? = "",

    @ColumnInfo(name = "video")
    @field:SerializedName("video")
    override var video: Boolean? = false,

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    override var id: Int? = -1,

    @ColumnInfo(name = "vote_average")
    @field:SerializedName("vote_average")
    override var voteAverage: Float? = 0.0f,

    @ColumnInfo(name = "title")
    @field:SerializedName("title")
    override var title: String? = "",

    @ColumnInfo(name = "overview")
    @field:SerializedName("overview")
    override var overview: String? = "",

    @ColumnInfo(name = "popularity")
    @field:SerializedName("popularity")
    override var popularity: Double? = 0.0,

    @ColumnInfo(name = "mediaType")
    @field:SerializedName("media_type")
    override var mediaType: String? = "",

    override var name: String? = "",
    override var firstAirDate: String? = "",
) : Movie()
