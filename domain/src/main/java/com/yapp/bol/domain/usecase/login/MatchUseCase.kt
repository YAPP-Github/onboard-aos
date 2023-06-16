package com.yapp.bol.domain.usecase.login

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MatchUseCase@Inject constructor(
    private val repository: Repository
) {
    fun getGameList(groupId: String): Flow<ApiResult<List<GameItem>>> {
        return repository.getGameList(groupId)
    }
}
