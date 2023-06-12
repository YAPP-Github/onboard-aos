package com.yapp.bol.data.model

import com.google.gson.annotations.SerializedName

enum class LoginType {
    KAKAO_ACCESS_TOKEN,
    GOOGLE,
    NAVER_ACCESS_TOKEN
}

data class OAuthApiRequest(
    @SerializedName("type")
    val type: LoginType,
    @SerializedName("token")
    val token: String
)
