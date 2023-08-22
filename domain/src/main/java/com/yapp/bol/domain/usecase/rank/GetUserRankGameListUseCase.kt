package com.yapp.bol.domain.usecase.rank

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRankGameListUseCase @Inject constructor(
    private val repository: GameRepository
) {
    operator fun invoke(groupId: Int): Flow<ApiResult<List<GameItem>>> =
        repository.getGameList(groupId = groupId)
}
