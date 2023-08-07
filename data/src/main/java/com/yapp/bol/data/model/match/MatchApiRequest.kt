package com.yapp.bol.data.model.match

data class MatchApiRequest(
    val gameId: Int,
    val groupId: Int,
    val matchedDate: String,
    val matchMembers: List<MatchMemberDTO>
)
