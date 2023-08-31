package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.match.MatchApiRequest
import com.yapp.bol.data.model.match.MatchMemberDTO
import com.yapp.bol.domain.model.MatchItem
import com.yapp.bol.domain.model.MatchMemberItem

object MatchMapper {

    fun MatchItem.toMatchDomain(): MatchApiRequest {
        return MatchApiRequest(
            this.gameId,
            this.groupId,
            this.matchedDate,
            this.matchMembers.map { it.toMatchItem() }
        )
    }

    private fun MatchMemberItem.toMatchItem(): MatchMemberDTO {
        return MatchMemberDTO(
            this.memberId,
            this.score,
            this.ranking
        )
    }
}
