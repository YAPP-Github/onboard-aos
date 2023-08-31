package com.yapp.bol.domain.usecase.login

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.domain.model.MatchItem
import com.yapp.bol.domain.model.MemberItems
import com.yapp.bol.domain.model.user.UserItem
import com.yapp.bol.domain.repository.GameRepository
import com.yapp.bol.domain.repository.MatchRepository
import com.yapp.bol.domain.repository.MemberRepository
import com.yapp.bol.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MatchUseCase @Inject constructor(
    private val gameRepository: GameRepository,
    private val memberRepository: MemberRepository,
    private val matchRepository: MatchRepository,
    private val userRepository: UserRepository,
) {
    fun getGameList(groupId: Int): Flow<ApiResult<List<GameItem>>> {
        return gameRepository.getGameList(groupId)
    }

    fun getValidateNickName(groupId: Int, nickname: String): Flow<ApiResult<Boolean>> {
        return memberRepository.getValidateNickName(groupId, nickname)
    }

    fun getMemberList(
        groupId: Int,
        pageSize: Int,
        cursor: String?,
        nickname: String?,
    ): Flow<ApiResult<MemberItems>> {
        return memberRepository.getMemberList(groupId, pageSize, cursor, nickname)
    }

    suspend fun postGuestMember(groupId: Int, nickname: String) {
        memberRepository.postGuestMember(groupId, nickname)
    }

    suspend fun postMatch(matchItem: MatchItem) = matchRepository.postMatch(matchItem)

    fun getUserInfo(): Flow<ApiResult<UserItem>> = userRepository.getUserInfo()
}
