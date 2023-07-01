package com.yapp.bol.domain.usecase.rank

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.UserRankItem
import com.yapp.bol.domain.model.UserRankListItem
import com.yapp.bol.domain.repository.RankRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRankUseCase @Inject constructor(
    private val rankRepository: RankRepository
) {
    operator fun invoke(groupId: Int, gameId: Int): Flow<ApiResult<UserRankListItem>> =
        rankRepository.getUserRank(
            groupId = groupId,
            gameId = gameId,
        )
}
