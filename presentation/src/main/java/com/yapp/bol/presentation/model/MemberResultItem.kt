package com.yapp.bol.presentation.model

data class MemberResultItem(
    val id: Int,
    val role: String,
    val nickname: String,
    val level: Int,
    var score: Int?,
    val rank: Int,
)
