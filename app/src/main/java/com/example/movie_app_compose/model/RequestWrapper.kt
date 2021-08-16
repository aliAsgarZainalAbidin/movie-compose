package com.example.movie_app_compose.model

import com.google.gson.annotations.SerializedName

data class RequestWrapper (
    @field:SerializedName("success")
    var success : Boolean? = false,

    @field:SerializedName("expires_at")
    var expiresAt : String? = "",

    @field:SerializedName("request_token")
    var requestToken : String? = "",

    @field:SerializedName("session_id")
    var sessionId : String? = ""
)