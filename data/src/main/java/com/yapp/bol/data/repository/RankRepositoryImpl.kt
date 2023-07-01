package com.yapp.bol.data.repository

import com.yapp.bol.data.datasource.rank.RankDataSource
import com.yapp.bol.data.mapper.RankMapper.toDomain
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.UserRankListItem
import com.yapp.bol.domain.repository.RankRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RankRepositoryImpl(
    private val rankDataSource: RankDataSource
) : RankRepository {

    override fun getUserRank(
        groupId: Int,
        gameId: Int
    ): Flow<ApiResult<UserRankListItem>> =
        rankDataSource.getUserRank(
            groupId = groupId,
            gameId = gameId
        ).map {
            it.toDomain()
        }
}
