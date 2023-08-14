package com.yapp.bol.data.datasource.user

import com.yapp.bol.data.model.user.JoinedGroupResponse
import com.yapp.bol.data.model.user.OnBoardResponse
import com.yapp.bol.data.model.user.UserRequest
import com.yapp.bol.data.model.user.UserResponse
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface UserDataSource {

    suspend fun putUserName(userRequest: UserRequest)

    fun getJoinedGroup(): Flow<ApiResult<JoinedGroupResponse>>

    fun getOnBoard(): Flow<ApiResult<OnBoardResponse>>

    fun getUserInfo(): Flow<ApiResult<UserResponse>>
}
