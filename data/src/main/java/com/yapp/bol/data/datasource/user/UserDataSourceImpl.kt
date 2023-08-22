package com.yapp.bol.data.datasource.user

import com.yapp.bol.data.datasource.game.GameDataSource
import com.yapp.bol.data.model.group.response.JoinedGroupResponse
import com.yapp.bol.data.model.login.OnBoardResponse
import com.yapp.bol.data.model.login.UserRequest
import com.yapp.bol.data.model.user.UserResponse
import com.yapp.bol.data.remote.UserApi
import com.yapp.bol.domain.handle.BaseRepository
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
) : BaseRepository(), UserDataSource {

    override suspend fun putUserName(userRequest: UserRequest) {
        userApi.putUserName(userRequest)
    }

    override fun getJoinedGroup(): Flow<ApiResult<JoinedGroupResponse>> =
        flow {
            safeApiCall {
                userApi.getJoinedGroup()
            }.also { emit(it) }
        }

    override fun getOnBoard(): Flow<ApiResult<OnBoardResponse>> = flow {
        val result = safeApiCall { userApi.getOnboard() }
        emit(result)
    }

    override fun getUserInfo(): Flow<ApiResult<UserResponse>> = flow {
        val result = safeApiCall { userApi.getUserInfo() }
        emit(result)
    }
}
