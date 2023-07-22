package com.yapp.bol.domain.usecase.group

import com.yapp.bol.domain.repository.Repository
import javax.inject.Inject

class GetGroupItemUseCase @Inject constructor(
    private val repository: Repository,
) {
    operator fun invoke(groupId: Int) = repository.getGroupInfo(groupId)
}
