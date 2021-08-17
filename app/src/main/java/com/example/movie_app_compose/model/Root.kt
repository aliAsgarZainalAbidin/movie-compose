package com.example.movie_app_compose.model

import com.google.gson.annotations.SerializedName

data class Root<T>(
    @field:SerializedName("page")
    var page : Int? = 0,

    @field:SerializedName("results")
    var results: ArrayList<T>? = arrayListOf(),

    @field:SerializedName("total_pages")
    var total_pages : Int? = 0,

    @field:SerializedName("total_results")
    var total_results : Int? = 0
)
