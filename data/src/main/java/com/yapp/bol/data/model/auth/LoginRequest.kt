package com.yapp.bol.data.model.auth

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("type")
    val type: String,
    @SerializedName("token")
    val token: String
)
