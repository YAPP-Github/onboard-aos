package com.yapp.bol.domain.model

data class UserRankItem(
    val id: Long,
    val rank: Int,
    val name: String,
    val winRate: Double,
    val playCount: Int,
)
