package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.ErrorItem
import com.yapp.bol.domain.model.MemberItems
import kotlinx.coroutines.flow.Flow

interface MemberRepository {

    fun getValidateNickName(
        groupId: Int,
        nickname: String,
    ): Flow<ApiResult<Boolean>>

    fun getMemberList(
        groupId: Int,
        pageSize: Int,
        cursor: String?,
        nickname: String?,
    ): Flow<ApiResult<MemberItems>>

    suspend fun postGuestMember(groupId: Int, nickname: String)

    fun joinGroup(
        groupId: String,
        accessCode: String,
        nickname: String,
    ): Flow<ApiResult<ErrorItem>>
}
