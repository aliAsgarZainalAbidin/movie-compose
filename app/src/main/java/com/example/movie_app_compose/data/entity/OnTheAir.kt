package com.example.movie_app_compose.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movie_app_compose.util.TvShow
import com.google.gson.annotations.SerializedName

@Entity
data class OnTheAir(
    @ColumnInfo(name = "vote_average")
    @field:SerializedName("vote_average")
    override var voteAverage: Float? = 0.0f,

    @ColumnInfo(name = "backdrop_path")
    @field:SerializedName("backdrop_path")
    override var backdropPath: String? = "",

    @ColumnInfo(name = "first_air_date")
    @field:SerializedName("first_air_date")
    override var firstAirDate: String? = "",

    @ColumnInfo(name = "genres")
    @field:SerializedName("genres")
    override var genres: List<Int> = listOf(),

    @ColumnInfo(name = "original_language")
    @field:SerializedName("original_language")
    override var language: String? = "",

    @ColumnInfo(name = "overview")
    @field:SerializedName("overview")
    override var overview: String? = "",

    @ColumnInfo(name = "popularity")
    @field:SerializedName("popularity")
    override var popularity: Double? = 0.0,

    @ColumnInfo(name = "poster_path")
    @field:SerializedName("poster_path")
    override var posterPath: String? = "",

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    override var name: String? = "",

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    override var id: Int? = -1,
) : TvShow()
