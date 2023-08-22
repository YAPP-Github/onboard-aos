package com.yapp.bol.data.datasource.game

import com.yapp.bol.data.model.group.response.GameApiResponse
import com.yapp.bol.data.remote.GameApi
import com.yapp.bol.domain.handle.BaseRepository
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GameDataSourceImpl @Inject constructor(
    private val gameApi: GameApi,
) : BaseRepository(), GameDataSource {

    override fun getGameList(groupId: Int): Flow<ApiResult<GameApiResponse>> = flow {
        val result = safeApiCall { gameApi.getGameList(groupId) }
        emit(result)
    }
}
