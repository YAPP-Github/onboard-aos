package com.yapp.bol.data.repository.user

import com.yapp.bol.data.datasource.user.UserDataSource
import com.yapp.bol.data.mapper.MapperToDomain.toMyGroupDomain
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.user.group.MyGroupItem
import com.yapp.bol.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource,
) : UserRepository {

    override suspend fun getMyGroupList(): Flow<ApiResult<List<MyGroupItem>>> = dataSource.getMyGroupList().map {
        it.toMyGroupDomain()
    }
}
