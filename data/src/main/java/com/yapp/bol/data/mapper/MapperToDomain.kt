package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.MockApiResponse
import com.yapp.bol.data.model.group.GroupSearchApiResponse
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GroupSearchItem
import com.yapp.bol.domain.model.LoginItem

object MapperToDomain {
    fun MockApiResponse?.toDomain(): LoginItem? = this?.toItem()

    private fun MockApiResponse.toItem(): LoginItem {
        return LoginItem(
            this.accessToken,
            this.refreshToken,
        )
    }

    fun ApiResult<GroupSearchApiResponse>.toDomain(): ApiResult<List<GroupSearchItem>> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.toItem())
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    private fun GroupSearchApiResponse.toItem(): List<GroupSearchItem> {
        return this.groups.content.map { groupItem ->
            GroupSearchItem(
                id = groupItem.id,
                name = groupItem.name,
                description = groupItem.description,
                organization = groupItem.organization,
                profileImageUrl = groupItem.profileImageUrl,
                memberCount = groupItem.memberCount
            )
        }
    }
}
