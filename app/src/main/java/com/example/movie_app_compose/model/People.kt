package com.example.movie_app_compose.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class People(

    @ColumnInfo(name = "adult")
    @field:SerializedName("adult")
    var adult: Boolean? = false,

    @ColumnInfo(name = "gender")
    @field:SerializedName("gender")
    var gender: Int? = -1,


    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    var id: Int? = -1,

    @ColumnInfo(name = "known_for")
    @field:SerializedName("known_for")
    var known_for: List<KnownFor>? = listOf(),

    @ColumnInfo(name = "known_for_department")
    @field:SerializedName("known_for_department")
    var known_for_department: String? = "",

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    var name: String? = "",

    @ColumnInfo(name = "popularity")
    @field:SerializedName("popularity")
    var popularity: Float? = 0f,

    @ColumnInfo(name = "profile_path")
    @field:SerializedName("profile_path")
    var profile_path: String? = ""
)


//"adult":false,
//"gender":1,
//"id":169337,
//"known_for":[
//{
//    "backdrop_path":"/3IIBf6VlwEyKAX4cN2XCM1gKdgM.jpg",
//    "first_air_date":"2005-03-27",
//    "genre_ids":[
//    18
//    ],
//    "id":1416,
//    "media_type":"tv",
//    "name":"Grey's Anatomy",
//    "origin_country":[
//    "US"
//    ],
//    "original_language":"en",
//    "original_name":"Grey's Anatomy",
//    "overview":"Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
//    "poster_path":"/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
//    "vote_average":8.2,
//    "vote_count":6478
//}
//],
//"known_for_department":"Acting",
//"name":"Katherine LaNasa",
//"popularity":254.422,
//"profile_path":"/a1T5Smn7sCEtV8NHvTa5WaxgOML.jpg"
