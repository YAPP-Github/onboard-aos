package com.yapp.bol.data.model

import com.google.gson.annotations.SerializedName

data class OAuthApiResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)
