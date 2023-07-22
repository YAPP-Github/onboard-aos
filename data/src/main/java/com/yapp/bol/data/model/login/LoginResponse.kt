package com.yapp.bol.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)
