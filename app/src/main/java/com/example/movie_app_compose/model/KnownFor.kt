package com.example.movie_app_compose.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class KnownFor(
    @ColumnInfo(name = "backdrop_path")
    @field:SerializedName("backdrop_path")
    var backdrop_path: String? = "",

    @ColumnInfo(name = "first_air_date")
    @field:SerializedName("first_air_date")
    var first_air_date: String? = "",

    @ColumnInfo(name = "genre_ids")
    @field:SerializedName("genre_ids")
    var genre_ids: List<Int>? = listOf(),

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    var id: Int? = 0,

    @ColumnInfo(name = "media_type")
    @field:SerializedName("media_type")
    var media_type: String? = "",

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    var name: String? = "",

    @ColumnInfo(name = "origin_country")
    @field:SerializedName("origin_country")
    var origin_country: List<String>? = listOf(),

    @ColumnInfo(name = "original_language")
    @field:SerializedName("original_language")
    var original_language: String? = "",

    @ColumnInfo(name = "original_name")
    @field:SerializedName("original_name")
    var original_name: String? = "",

    @ColumnInfo(name = "overview")
    @field:SerializedName("overview")
    var overview: String? = "",

    @ColumnInfo(name = "poster_path")
    @field:SerializedName("poster_path")
    var poster_path: String? = "",

    @ColumnInfo(name = "vote_average")
    @field:SerializedName("vote_average")
    var vote_average: Float? = 0f,

    @ColumnInfo(name = "vote_count")
    @field:SerializedName("vote_count")
    var vote_count: Int? = -1,

    @ColumnInfo(name = "adult")
    @field:SerializedName("adult")
    var adult: Boolean? = false,

    @ColumnInfo(name = "original_title")
    @field:SerializedName("original_title")
    var original_title: String? = "",

    @ColumnInfo(name = "release_date")
    @field:SerializedName("release_date")
    var release_date: String? = "",

    @ColumnInfo(name = "title")
    @field:SerializedName("title")
    var title: String? = "",

    @ColumnInfo(name = "video")
    @field:SerializedName("video")
    var video: Boolean? = false
)
