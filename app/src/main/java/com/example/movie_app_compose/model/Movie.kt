package com.example.movie_app_compose.model

import com.google.gson.annotations.SerializedName


data class Movie(
    @field:SerializedName("release_date")
    var releaseDate : String? = "",

    @field:SerializedName("adult")
    var adult : Boolean? = false,

    @field:SerializedName("backdrop_path")
    var backdropPath : String? = "",

    @field:SerializedName("genre_ids")
    var genreIds : List<Int>? = listOf(),

    @field:SerializedName("vote_counts")
    var voteCounts : Int? = -1,

    @field:SerializedName("original_language")
    var originalLanguage : String? = "",

    @field:SerializedName("original_title")
    var originalTitle : String? = "",

    @field:SerializedName("poster_path")
    var posterPath : String? = "",

    @field:SerializedName("video")
    var video : Boolean? = false,

    @field:SerializedName("id")
    var id : Int? = -1,

    @field:SerializedName("vote_avarage")
    var voteAvarage : Double? = 0.0,

    @field:SerializedName("title")
    var title : String? = "",

    @field:SerializedName("overview")
    var overview : String? = "",

    @field:SerializedName("popularity")
    var popularity : Double? = 0.0,

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
