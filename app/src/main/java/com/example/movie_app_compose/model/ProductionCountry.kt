package com.example.movie_app_compose.model

import com.google.gson.annotations.SerializedName

data class ProductionCountry(

    @field:SerializedName("iso_3166_1")
    val iso_3166_1: String,

    @field:SerializedName("name")
    val name: String
)