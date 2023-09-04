package com.yapp.bol.domain.usecase.group

import com.yapp.bol.domain.repository.MemberRepository
import javax.inject.Inject

class JoinGroupUseCase @Inject constructor(
    private val repository: MemberRepository,
) {
    operator fun invoke(
        groupId: String,
        accessCode: String,
        nickName: String,
        guestId: Int?,
    ) = repository.joinGroup(groupId, accessCode, nickName, guestId)
}
