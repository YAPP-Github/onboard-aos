package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GameItem
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    fun getGameList(groupId: Int): Flow<ApiResult<List<GameItem>>>
}
