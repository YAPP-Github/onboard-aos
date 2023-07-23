package com.yapp.bol.data.datasource.rank

import com.yapp.bol.data.model.rank.UserRankApiResponse
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface RankDataSource {

    fun getUserRank(
        groupId: Int,
        gameId: Int,
    ): Flow<ApiResult<UserRankApiResponse>>
}
