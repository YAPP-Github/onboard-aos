package com.yapp.bol.presentation.view.match.game_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.MatchItem
import com.yapp.bol.domain.model.MatchMemberItem
import com.yapp.bol.domain.usecase.login.MatchUseCase
import com.yapp.bol.presentation.model.MemberInfo
import com.yapp.bol.presentation.model.MemberResultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameResultViewModel @Inject constructor(
    private val matchUseCase: MatchUseCase
) : ViewModel() {

    private val dynamicPlayers = ArrayList<MemberResultItem>()
    private var currentRank = 0

    private val _players = MutableLiveData(dynamicPlayers.toList())
    val players: LiveData<List<MemberResultItem>> = _players

    private val _recordCompleteIsEnabled = MutableLiveData(false)
    val recordCompleteIsEnabled: LiveData<Boolean> = _recordCompleteIsEnabled

    private val _isRecordComplete = MutableLiveData(false)
    val isRecordComplete: LiveData<Boolean> = _isRecordComplete

    fun initPlayers(player: ArrayList<MemberInfo>) {
        val newPlayers = player.mapIndexed { index, memberItem ->
            MemberResultItem(
                memberItem.id,
                memberItem.role,
                memberItem.nickname,
                memberItem.level,
                null,
                index,
            )
        }
        dynamicPlayers.addAll(newPlayers)
        updatePlayers()
    }

    fun postMatch(gameId: Int, groupId: Int, currentTime: String) {
        viewModelScope.launch {
            matchUseCase.postMatch(
                MatchItem(
                    gameId = gameId,
                    groupId = groupId,
                    matchedDate = currentTime,
                    matchMembers = List(players.value?.size ?: 0) {
                        val temp = players.value?.get(it) ?: return@launch
                        MatchMemberItem(temp.id, temp.score ?: 0, temp.rank + 1)
                    }
                )
            )
            _isRecordComplete.value = true

        }
    }

    fun updatePlayerScore(position: Int, value: Int?) {
        dynamicPlayers[position].score = value
        _recordCompleteIsEnabled.value = checkedRecordCompleteIsEnabled()
    }

    private fun sortPlayerScore() {
        dynamicPlayers.sortByDescending { it.score }
    }

    fun updatePlayers() {
        sortPlayerScore()
        _players.value = dynamicPlayers.mapIndexed { index, memberResultItem ->
            memberResultItem.copy(rank = setPlayerRank(index))
        }
        currentRank = 0
    }

    private fun setPlayerRank(rank: Int): Int {
        if (rank == 0) return currentRank

        return if (dynamicPlayers[rank].score == dynamicPlayers[rank - 1].score && dynamicPlayers[rank].score != null) {
            currentRank
        } else {
            currentRank = rank
            currentRank
        }
    }

    private fun checkedRecordCompleteIsEnabled(): Boolean {
        for (i in 0 until dynamicPlayers.size) {
            if (dynamicPlayers[i].score == null) return false
        }
        return true
    }
}
