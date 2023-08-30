package com.yapp.bol.domain.usecase.group

import com.yapp.bol.domain.repository.GroupRepository
import javax.inject.Inject

class CheckGroupJoinByAccessCodeUseCase @Inject constructor(
    private val repository: GroupRepository,
) {
    operator fun invoke(
        groupId: String,
        accessCode: String,
    ) = repository.checkGroupJoinAccessCode(groupId, accessCode)
}
