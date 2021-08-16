package com.example.movie_app_compose.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(

    @ColumnInfo(name = "release_date")
    @field:SerializedName("release_date")
    var releaseDate : String? = "",

    @ColumnInfo(name = "adult")
    @field:SerializedName("adult")
    var adult : Boolean? = false,

    @ColumnInfo(name = "backdrop_path")
    @field:SerializedName("backdrop_path")
    var backdropPath : String? = "",

    @ColumnInfo(name = "genre_ids")
    @field:SerializedName("genre_ids")
    var genreIds : List<Genre>? = listOf(),

    @ColumnInfo(name = "vote_counts")
    @field:SerializedName("vote_counts")
    var voteCounts : Int? = -1,

    @ColumnInfo(name = "original_language")
    @field:SerializedName("original_language")
    var originalLanguage : String? = "",

    @ColumnInfo(name = "original_title")
    @field:SerializedName("original_title")
    var originalTitle : String? = "",

    @ColumnInfo(name = "poster_path")
    @field:SerializedName("poster_path")
    var posterPath : String? = "",

    @ColumnInfo(name = "video")
    @field:SerializedName("video")
    var video : Boolean? = false,

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    var id : Int? = -1,

    @ColumnInfo(name = "vote_avarage")
    @field:SerializedName("vote_avarage")
    var voteAvarage : Double? = 0.0,

    @ColumnInfo(name = "title")
    @field:SerializedName("title")
    var title : String? = "",

    @ColumnInfo(name = "overview")
    @field:SerializedName("overview")
    var overview : String? = "",

    @ColumnInfo(name = "popularity")
    @field:SerializedName("popularity")
    var popularity : Double? = 0.0,

    @ColumnInfo(name = "mediaType")
    @field:SerializedName("mediaType")
    var mediaType : String? = ""
)


//sample data
//{
//    "adult":false,
//    "backdrop_path":"/rAgsOIhqRS6tUthmHoqnqh9PIAE.jpg",
//    "genre_ids":[
//    28,
//    12,
//    14
//    ],
//    "id":436969,
//    "original_language":"en",
//    "original_title":"The Suicide Squad",
//    "overview":"Supervillains Harley Quinn, Bloodsport, Peacemaker and a collection of nutty cons at Belle Reve prison join the super-secret, super-shady Task Force X as they are dropped off at the remote, enemy-infused island of Corto Maltese.",
//    "popularity":11135.763,
//    "poster_path":"/kb4s0ML0iVZlG6wAKbbs9NAm6X.jpg",
//    "release_date":"2021-07-28",
//    "title":"The Suicide Squad",
//    "video":false,
//    "vote_average":8.2,
//    "vote_count":1711
//}

//{
//    "genres":[
//    {
//        "id":28,
//        "name":"Action"
//    },
//    {
//        "id":12,
//        "name":"Adventure"
//    },
//    {
//        "id":16,
//        "name":"Animation"
//    },
//    {
//        "id":35,
//        "name":"Comedy"
//    },
//    {
//        "id":80,
//        "name":"Crime"
//    },
//    {
//        "id":99,
//        "name":"Documentary"
//    },
//    {
//        "id":18,
//        "name":"Drama"
//    },
//    {
//        "id":10751,
//        "name":"Family"
//    },
//    {
//        "id":14,
//        "name":"Fantasy"
//    },
//    {
//        "id":36,
//        "name":"History"
//    },
//    {
//        "id":27,
//        "name":"Horror"
//    },
//    {
//        "id":10402,
//        "name":"Music"
//    },
//    {
//        "id":9648,
//        "name":"Mystery"
//    },
//    {
//        "id":10749,
//        "name":"Romance"
//    },
//    {
//        "id":878,
//        "name":"Science Fiction"
//    },
//    {
//        "id":10770,
//        "name":"TV Movie"
//    },
//    {
//        "id":53,
//        "name":"Thriller"
//    },
//    {
//        "id":10752,
//        "name":"War"
//    },
//    {
//        "id":37,
//        "name":"Western"
//    }
//    ]
//}
