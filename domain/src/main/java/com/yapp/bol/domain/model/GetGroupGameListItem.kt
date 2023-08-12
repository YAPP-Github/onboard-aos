package com.yapp.bol.domain.model

data class GetGroupGameListItem(
    val gameList: List<GetGroupGame>,
)

data class GetGroupGame(
    val id: Int,
    val gameImageUrl: String ,
    val maxPlayGameMember: Int,
    val minPlayGameMember: Int,
    val gameName: String,
)
