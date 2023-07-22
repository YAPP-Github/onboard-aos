package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.group.JoinedGroupResponse
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.JoinedGroupItem

object GroupMapper {

    fun ApiResult<JoinedGroupResponse>.toDomain(): ApiResult<List<JoinedGroupItem>> =
        when (this) {
            is ApiResult.Success -> ApiResult.Success(this.data.toDomain())
            is ApiResult.Error -> ApiResult.Error(exception)
        }

    private fun JoinedGroupResponse.toDomain(): List<JoinedGroupItem> =
        this.contents.map { joinedGroupDTO ->
            JoinedGroupItem(
                id = joinedGroupDTO.id,
                name = joinedGroupDTO.name,
                description = joinedGroupDTO.description,
                organization = joinedGroupDTO.organization,
                profileImageUrl = joinedGroupDTO.profileImageUrl
            )
        }
}
