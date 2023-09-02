package com.yapp.bol.domain.usecase.group

import com.yapp.bol.domain.model.GetGroupJoinedItem
import com.yapp.bol.domain.model.GroupDetailItem
import com.yapp.bol.domain.repository.GroupRepository
import com.yapp.bol.domain.repository.UserRepository
import com.yapp.bol.domain.utils.doWork
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetGroupJoinedUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(groupId: Int): GetGroupJoinedItem? = withContext(IO) {
        var groupDetail: GroupDetailItem? = null
        var myNickname = ""
        var hasJoinedGroup = false

        groupRepository.getGroupDetail(groupId.toLong()).doWork(
            isSuccess = {
                groupDetail = it
            },
        )
        userRepository.getUserInfo().doWork(
            isSuccess = { myNickname = it.nickname },
        )
        userRepository.getJoinedGroup().doWork(
            isSuccess = { hasJoinedGroup = hasJoinedGroup(it.map { it.id.toInt() }, groupId) },
        )

        return@withContext groupDetail?.let {
            GetGroupJoinedItem(it, myNickname, hasJoinedGroup)
        }
    }

    private fun hasJoinedGroup(groupList: List<Int>, groupId: Int) = groupList.find { it == groupId } != null
}
