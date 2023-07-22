package com.yapp.bol.domain.model

data class MatchItem(
    val gameId: Int,
    val groupId: Int,
    val matchedDate: String,
    val matchMembers: List<MatchMemberItem>
)
