package com.example.movie_app_compose.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @field:SerializedName("id")
    var id : Int? = -1,

    @field:SerializedName("name")
    var name : String? = ""
)
