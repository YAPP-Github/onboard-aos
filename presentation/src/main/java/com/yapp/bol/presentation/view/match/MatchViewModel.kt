package com.yapp.bol.presentation.view.match

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yapp.bol.presentation.utils.Constant.EMPTY_STRING
import com.yapp.bol.presentation.utils.Constant.GAME_RESULT_RECORD
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor() : ViewModel() {

    private val _toolBarTitle = MutableLiveData(GAME_RESULT_RECORD)
    val toolBarTitle: LiveData<String> = _toolBarTitle

    private val _pageState = MutableLiveData(0)
    val pageState: LiveData<Int> = _pageState

    var currentPage = 0
    var gameName = EMPTY_STRING
    var gameImageUrl = EMPTY_STRING
    var gameId = 0L
    var groupId = 0
    var currentTime = ""

    fun updateGameId(id: Long) {
        gameId = id
    }

    fun updateGroupId(id: Int) {
        groupId = id
    }

    fun updateToolBarTitle(title: String) {
        _toolBarTitle.value = title
    }

    fun updateGameName(name: String) {
        gameName = name
    }

    fun updateGameImageUrl(url: String) {
        gameImageUrl = url
    }

    fun updatePageState(page: Int) {
        _pageState.value = page
    }

    fun updateCurrentPage(page: Int) {
        currentPage = page
    }

    fun updateCurrentTime(time: String) {
        currentTime = time
    }
}
