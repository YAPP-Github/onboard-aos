package com.yapp.bol.domain.usecase.login

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.domain.model.MemberItem
import com.yapp.bol.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MatchUseCase @Inject constructor(
    private val repository: Repository
) {
    fun getGameList(groupId: Int): Flow<ApiResult<List<GameItem>>> {
        return repository.getGameList(groupId)
    }

    fun getValidateNickName(groupId: Int, nickname: String): Flow<ApiResult<Boolean>> {
        return repository.getValidateNickName(groupId, nickname)
    }

    fun getMemberList(
        groupId: Int,
        pageSize: Int,
        cursor: String?,
        nickname: String?,
    ): Flow<ApiResult<List<MemberItem>>> {
        return repository.getMemberList(groupId, pageSize, cursor, nickname)
    }
}
