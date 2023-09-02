package com.yapp.bol.data.datasource.game

import com.yapp.bol.data.model.game.GameApiResponse
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface GameDataSource {

    fun getGameList(groupId: Int): Flow<ApiResult<GameApiResponse>>
}
