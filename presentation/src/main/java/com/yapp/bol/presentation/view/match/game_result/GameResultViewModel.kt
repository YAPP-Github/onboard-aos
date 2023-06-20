package com.yapp.bol.presentation.view.match.game_result

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameResultViewModel @Inject constructor() : ViewModel() {

    private val players = ArrayList<Pair<Int,Int>>(10).apply {
        addAll(List(10) { -1 to -1 })
    }

    fun updatePlayerValue(position: Int, value: Int) {
        val temp = getPlayerRank(position)
        if(temp == -1) players[position] = position to value
        else players[temp] = position to value
    }

    fun getTargetPosition(position: Int, value: Int): Int {
        val rank = getPlayerRank(position)
        for(i in 0 until rank) {
            if(players[i].second < value) {
                swipeValue(i,rank)
                return  i
            }
        }
        return -1
    }

    private fun getPlayerRank(position: Int): Int {
        for(i in 0 until  players.size) {
            if(players[i].first == position) return i
        }
        return -1
    }

    private fun swipeValue(form: Int, to: Int) {
        players.removeAt(to).also {
            players.add(form, it)
        }
    }
}
