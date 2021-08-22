package com.example.movie_app_compose.model

import com.google.gson.annotations.SerializedName

data class ProductionCompany(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("logo_path")
    val logo_path: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("origin_country")
    val origin_country: String
)