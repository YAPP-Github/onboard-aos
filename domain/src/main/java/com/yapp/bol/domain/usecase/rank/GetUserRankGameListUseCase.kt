package com.yapp.bol.domain.usecase.rank

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRankGameListUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(groupId: Int): Flow<ApiResult<List<GameItem>>> =
        repository.getGameList(groupId = groupId)
}
