package com.yapp.bol.domain.usecase.group

import com.yapp.bol.domain.repository.Repository
import javax.inject.Inject

class JoinGroupUseCase @Inject constructor(
    private val repository: Repository,
) {
    operator fun invoke(
        groupId: String,
        accessCode: String,
        nickName: String,
    ) = repository.joinGroup(groupId, accessCode, nickName)
}
