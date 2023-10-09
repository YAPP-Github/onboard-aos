package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.JoinedGroupItem
import com.yapp.bol.domain.model.OnBoardingItem
import com.yapp.bol.domain.model.user.UserItem
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun putUserName(nickName: String)

    fun getJoinedGroup(): Flow<ApiResult<List<JoinedGroupItem>>>

    fun getOnBoard(): Flow<ApiResult<OnBoardingItem>>

    fun getUserInfo(): Flow<ApiResult<UserItem>>
}
