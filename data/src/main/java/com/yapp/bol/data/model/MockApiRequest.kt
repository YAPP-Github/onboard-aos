package com.yapp.bol.data.model

import com.google.gson.annotations.SerializedName

enum class MockType(val type: String) {
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE"),
    NAVER("NVER"),
}

data class MockApiRequest(
    @SerializedName("type")
    val type: MockType,
    @SerializedName("token")
    val token: String,
)
