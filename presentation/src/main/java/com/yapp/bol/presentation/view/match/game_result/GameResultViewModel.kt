package com.yapp.bol.presentation.view.match.game_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yapp.bol.presentation.model.MemberItem
import com.yapp.bol.presentation.model.MemberResultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameResultViewModel @Inject constructor() : ViewModel() {

    private val dynamicPlayers = ArrayList<MemberResultItem>()
    private var currentRank = 0

    private val _players = MutableLiveData(dynamicPlayers.toList())
    val players: LiveData<List<MemberResultItem>> = _players

    private val _recordCompleteIsEnabled = MutableLiveData(false)
    val recordCompleteIsEnabled: LiveData<Boolean> = _recordCompleteIsEnabled

    fun initPlayers(player: ArrayList<MemberItem>) {
        val newPlayers = player.mapIndexed { index, memberItem ->
            MemberResultItem(memberItem.id, memberItem.name, null, index)
        }
        dynamicPlayers.addAll(newPlayers)
        updatePlayers()
    }

    fun updatePlayerScore(position: Int, value: Int?) {
        dynamicPlayers[position].score = value
        _recordCompleteIsEnabled.value = checkedRecordCompleteIsEnabled()
    }

    fun sortPlayerScore() {
        dynamicPlayers.sortByDescending { it.score }
        updatePlayers()
    }

    private fun updatePlayers() {
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
