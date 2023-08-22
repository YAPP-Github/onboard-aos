package com.yapp.bol.data.datasource.member

import com.yapp.bol.data.model.base.BaseResponse
import com.yapp.bol.data.model.group.GuestAddApiRequest
import com.yapp.bol.data.model.group.JoinGroupApiRequest
import com.yapp.bol.data.model.group.MemberListResponse
import com.yapp.bol.data.model.group.response.MemberValidApiResponse
import com.yapp.bol.data.remote.MemberApi
import com.yapp.bol.domain.handle.BaseRepository
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MemberDataSourceImpl @Inject constructor(
    private val memberApi: MemberApi,
) : BaseRepository(), MemberDataSource {

    override fun getValidateNickName(
        groupId: Int,
        nickname: String,
    ): Flow<ApiResult<MemberValidApiResponse>> = flow {
        val result = safeApiCall { memberApi.getValidateNickName(groupId, nickname) }
        emit(result)
    }

    override fun getMemberList(
        groupId: Int,
        pageSize: Int,
        cursor: String?,
        nickname: String?,
    ): Flow<ApiResult<MemberListResponse>> = flow {
        val result = safeApiCall { memberApi.getMemberList(groupId, pageSize, cursor, nickname) }
        emit(result)
    }

    override suspend fun postGuestMember(groupId: Int, nickname: String) {
        memberApi.postGuestMember(groupId, GuestAddApiRequest(nickname))
    }

    override fun joinGroup(
        groupId: String,
        accessCode: String,
        nickname: String,
    ): Flow<ApiResult<BaseResponse>> = flow {
        val result = safeApiCall { memberApi.joinGroup(groupId, JoinGroupApiRequest(nickname, accessCode)) }
        emit(result)
    }
}
