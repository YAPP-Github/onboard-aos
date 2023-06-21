package com.yapp.bol.presentation.view.match.game_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yapp.bol.presentation.model.MemberResultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameResultViewModel @Inject constructor() : ViewModel() {
    private val testPlayers = ArrayList<MemberResultItem>(20).apply {
        addAll(List(20) { MemberResultItem(it, "$it. Text", 0, it) })
    }
    private val _players = MutableLiveData(testPlayers.toList())
    val players: LiveData<List<MemberResultItem>> = _players

    private val _recordCompleteIsEnabled = MutableLiveData(false)
    val recordCompleteIsEnabled: LiveData<Boolean> = _recordCompleteIsEnabled

    fun updatePlayerScore(position: Int, value: Int) {
        testPlayers[position].score = value
        _recordCompleteIsEnabled.value = checkedRecordCompleteIsEnabled()
    }

    fun changePlayerPosition(position: Int, value: Int) {
        for (i in 0 until position) {
            if (testPlayers[i].score < value) {
                swipePlayer(i, position)
                return
            }
        }
    }

    private fun swipePlayer(target: Int, currentPosition: Int) {
        testPlayers.removeAt(currentPosition).also {
            testPlayers.add(target, it)
        }
        updatePlayers()
    }

    private fun updatePlayers() {
        _players.value = testPlayers.mapIndexed { index, memberResultItem ->
            memberResultItem.copy(rank = index)
        }
    }

    private fun checkedRecordCompleteIsEnabled(): Boolean {
        for (i in 0 until testPlayers.size) {
            if (testPlayers[i].score == 0) return false
        }
        return true
    }
}
