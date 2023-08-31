package com.yapp.bol.domain.usecase.rank

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.UserRankListItem
import com.yapp.bol.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRankUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
) {
    operator fun invoke(groupId: Int, gameId: Int): Flow<ApiResult<UserRankListItem>> =
        groupRepository.getUserRank(
            groupId = groupId,
            gameId = gameId,
        )
}
