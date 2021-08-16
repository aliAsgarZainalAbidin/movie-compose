package com.example.movie_app_compose.model

import com.google.gson.annotations.SerializedName

data class Root(
    @field:SerializedName("page")
    var page : Int? = 0,

    @field:SerializedName("result")
    var results: List<Any>? = listOf(),

    @field:SerializedName("total_page")
    var total_pages : Int? = 0,

    @field:SerializedName("total_results")
    var total_results : Int? = 0
)
