package com.example.movie_app_compose.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Genre(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    var id : Int? = -1,

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    var name : String? = ""
)
