package com.yapp.bol.data.datasource.rank

import com.yapp.bol.data.datasource.rank.RankDataSource
import com.yapp.bol.data.model.rank.UserRankApiResponse
import com.yapp.bol.data.remote.RankApi
import com.yapp.bol.domain.handle.BaseRepository
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RankDataSourceImpl @Inject constructor(
    private val rankApi: RankApi,
) : BaseRepository(), RankDataSource {

    override fun getUserRank(groupId: Int, gameId: Int): Flow<ApiResult<UserRankApiResponse>> =
        flow {
            safeApiCall {
                rankApi.getUserRank(
                    groupId = groupId,
                    gameId = gameId
                )
            }.also { emit(it) }
        }
}
