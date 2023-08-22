package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.group.response.GameApiResponse
import com.yapp.bol.data.model.group.response.GameResponse
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GameItem

object GameMapper {

    fun ApiResult<GameApiResponse>.gameToDomain(): ApiResult<List<GameItem>> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.list.map { it.toItem() })
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    private fun GameResponse.toItem(): GameItem {
        return GameItem(
            id = this.id,
            name = this.name,
            maxMember = this.maxMember,
            minMember = this.minMember,
            img = this.img,
        )
    }
}
