package com.yapp.bol.domain.usecase.user

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.user.group.MyGroupItem
import com.yapp.bol.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyGroupListUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    suspend fun execute(): Flow<ApiResult<List<MyGroupItem>>> = repository.getMyGroupList()
}
