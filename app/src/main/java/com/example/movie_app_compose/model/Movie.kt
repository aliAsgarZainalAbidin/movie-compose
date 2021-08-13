package com.example.movie_app_compose.model


data class Movie(
    var releaseDate : String,
    var adult : Boolean,
    var backdropPath : String,
    var genreIds : List<Int>,
    var voteCounts : Int,
    var originalLanguage : String,
    var originalTitle : String,
    var posterPath : String,
    var video : Boolean,
    var id : Int,
    var voteAvarage : Double,
    var title : String,
    var overview : String,
    var popularity : Double,
    var mediaType : String
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
