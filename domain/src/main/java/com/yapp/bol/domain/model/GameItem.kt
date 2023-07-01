package com.yapp.bol.domain.model

data class GameItem(
    val id: Long,
    val name: String,
    val minMember: Int,
    val maxMember: Int,
    val img: String,
)
