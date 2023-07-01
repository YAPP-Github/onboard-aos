package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.UserRankListItem
import kotlinx.coroutines.flow.Flow

interface RankRepository {

    fun getUserRank(
        groupId: Int,
        gameId: Int,
    ): Flow<ApiResult<UserRankListItem>>
}
