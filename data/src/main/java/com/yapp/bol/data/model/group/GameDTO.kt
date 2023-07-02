package com.yapp.bol.data.model.group

data class GameDTO(
    val id: Long,
    val name: String,
    val minMember: Int,
    val maxMember: Int,
    val img: String,
)
