package com.yapp.bol.data.datasource.auth

object LoginType {
    private const val NAVER = "NAVER"
    private const val GOOGLE = "GOOGLE"
    private const val KAKAO = "KAKAO"

    private const val NAVER_REQUEST_TYPE = "NAVER_ACCESS_TOKEN"
    private const val KAKAO_REQUEST_TYPE = "KAKAO_ACCESS_TOKEN"
    private const val GOOGLE_REQUEST_TYPE = "GOOGLE_ID_TOKEN"

    fun String.toDomain(): String {
        return when (this) {
            NAVER -> NAVER_REQUEST_TYPE
            KAKAO -> KAKAO_REQUEST_TYPE
            GOOGLE -> GOOGLE_REQUEST_TYPE
            else -> throw IllegalArgumentException("LoginType is not valid")
        }
    }
}
