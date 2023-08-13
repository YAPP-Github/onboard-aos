package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.base.BaseResponse
import com.yapp.bol.data.model.group.MemberDTO
import com.yapp.bol.data.model.group.MemberListResponse
import com.yapp.bol.data.model.group.response.MemberValidApiResponse
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.BaseItem
import com.yapp.bol.domain.model.MemberItem
import com.yapp.bol.domain.model.MemberItems

object MemberMapper {

    fun ApiResult<BaseResponse>.mapperToBaseItem(): ApiResult<BaseItem> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(BaseItem(data.code, data.message))
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    fun ApiResult<MemberValidApiResponse>.validToDomain(): ApiResult<Boolean> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.isAvailable)
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    fun ApiResult<MemberListResponse>.memberListToDomain(): ApiResult<MemberItems> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(
                MemberItems(
                    members = data.contents.map { it.toItem() },
                    cursor = data.cursor,
                    hasNext = data.hasNext
                )
            )

            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }

    private fun MemberDTO.toItem(): MemberItem {
        return MemberItem(
            id = this.id,
            role = this.role,
            nickname = this.nickname,
            level = this.level
        )
    }
}
