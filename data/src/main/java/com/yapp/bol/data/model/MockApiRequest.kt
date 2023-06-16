package com.yapp.bol.data.model

import com.google.gson.annotations.SerializedName

data class MockApiRequest(
    @SerializedName("type")
    val type: String,
    @SerializedName("token")
    val token: String,
)
