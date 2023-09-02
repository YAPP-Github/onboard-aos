package com.yapp.bol.data.model.game

data class GameResponse(
    val id: Long,
    val name: String,
    val minMember: Int,
    val maxMember: Int,
    val img: String, // todo 무슨 이미지 인지 확인 필요
)
