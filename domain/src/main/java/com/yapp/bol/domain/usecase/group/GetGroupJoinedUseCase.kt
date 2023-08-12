package com.yapp.bol.domain.usecase.group

import android.util.Log
import com.yapp.bol.domain.model.GetGroupGame
import com.yapp.bol.domain.model.GetGroupJoinedItem
import com.yapp.bol.domain.model.GroupDetailItem
import com.yapp.bol.domain.repository.GroupRepository
import com.yapp.bol.domain.repository.Repository
import com.yapp.bol.domain.utils.doWork
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetGroupJoinedUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    private val repository: Repository,
) {

    suspend operator fun invoke(groupId: Int) = withContext(IO) {
        var groupDetail: GroupDetailItem? = null
        var myNickname = ""
        var hasJoinedGroup = false
        var groupGameList: List<GetGroupGame>? = null

        groupRepository.getGroupDetail(groupId.toLong()).doWork(
            isSuccess = {
                groupDetail = it
            },
        )
        repository.getUserInfo().doWork(
            isSuccess = { myNickname = it.nickname },
        )
        groupRepository.getJoinedGroup().doWork(
            isSuccess = { hasJoinedGroup = hasJoinedGroup(it.map { it.id.toInt() }, groupId) },
        )
        groupRepository.getGroupGameList(groupId).doWork(
            isSuccess = { groupGameList = it.gameList },
        )

        return@withContext GetGroupJoinedItem(groupDetail!!, groupGameList!!, myNickname, hasJoinedGroup)
    }

    private fun hasJoinedGroup(groupList: List<Int>, groupId: Int) = groupList.find { it == groupId } != null
}
