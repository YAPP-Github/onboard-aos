package com.yapp.bol.data.model

import com.google.gson.annotations.SerializedName

data class OAuthApiRequest(
    @SerializedName("type")
    val type: String,
    @SerializedName("token")
    val token: String
)
