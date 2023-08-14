package com.yapp.bol.data.repository

import com.yapp.bol.data.datasource.user.UserDataSource
import com.yapp.bol.data.mapper.UserMapper.toBoardDomain
import com.yapp.bol.data.mapper.UserMapper.toJoinedGroupItem
import com.yapp.bol.data.mapper.UserMapper.toUserDomain
import com.yapp.bol.data.model.auth.UserRequest
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.JoinedGroupItem
import com.yapp.bol.domain.model.user.UserItem
import com.yapp.bol.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {

    override suspend fun putUserName(nickName: String) {
        userDataSource.putUserName(UserRequest(nickName))
    }

    override fun getJoinedGroup(): Flow<ApiResult<List<JoinedGroupItem>>> =
        userDataSource.getJoinedGroup().map { it.toJoinedGroupItem() }

    override fun getOnBoard(): Flow<ApiResult<List<String>>> {
        return userDataSource.getOnBoard().map { it.toBoardDomain() }
    }

    override fun getUserInfo(): Flow<ApiResult<UserItem>> {
        return userDataSource.getUserInfo().map {
            it.toUserDomain()
        }
    }
}
