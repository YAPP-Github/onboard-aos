package com.yapp.bol.domain.usecase.group

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.JoinedGroupItem
import com.yapp.bol.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetJoinedGroupUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<ApiResult<List<JoinedGroupItem>>> =
        userRepository.getJoinedGroup()
}
