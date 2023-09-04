package com.yapp.bol.domain.usecase.group

import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.domain.model.GetGroupJoinedItem
import com.yapp.bol.domain.model.GroupDetailItem
import com.yapp.bol.domain.repository.GameRepository
import com.yapp.bol.domain.repository.GroupRepository
import com.yapp.bol.domain.repository.UserRepository
import com.yapp.bol.domain.utils.doWork
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetGroupJoinedUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    private val gameRepository: GameRepository,
) {

    suspend operator fun invoke(groupId: Int) = withContext(IO) {
        var groupDetail: GroupDetailItem? = null
        var myNickname = ""
        var hasJoinedGroup = false
        var groupGameList: List<GameItem>? = null

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
        gameRepository.getGameList(groupId).doWork(
            isSuccess = { groupGameList = it },
        )

        gameRepository.getGameList(groupId).doWork(
            isSuccess = { groupGameList = it },
        )

        return@withContext if (groupGameList != null && groupGameList?.isNotEmpty() == true) {
            GetGroupJoinedItem(groupDetail!!, groupGameList!!, myNickname, hasJoinedGroup)
        } else {
            null
        }
    }

    private fun hasJoinedGroup(groupList: List<Int>, groupId: Int) = groupList.find { it == groupId } != null
}
