package com.yapp.bol.data.repository

import com.yapp.bol.data.datasource.game.GameDataSource
import com.yapp.bol.data.mapper.GameMapper.gameToDomain
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameDataSource: GameDataSource,
) : GameRepository {

    override fun getGameList(groupId: Int): Flow<ApiResult<List<GameItem>>> {
        return gameDataSource.getGameList(groupId).map {
            it.gameToDomain()
        }
    }
}
