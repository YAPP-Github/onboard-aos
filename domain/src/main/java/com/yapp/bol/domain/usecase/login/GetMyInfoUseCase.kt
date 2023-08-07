package com.yapp.bol.domain.usecase.login

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.user.UserItem
import com.yapp.bol.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyInfoUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<ApiResult<UserItem>> = repository.getUserInfo()
}
