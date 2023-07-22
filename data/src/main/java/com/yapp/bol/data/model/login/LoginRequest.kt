package com.yapp.bol.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("type")
    val type: String,
    @SerializedName("token")
    val token: String
)
