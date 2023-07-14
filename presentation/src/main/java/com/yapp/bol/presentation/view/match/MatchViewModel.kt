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

    var gameName = EMPTY_STRING
    var groupId = 0

    fun updateGroupId(id: Int) {
        groupId = id
    }

    fun updateToolBarTitle(title: String) {
        _toolBarTitle.value = title
    }

    fun updateGameName(name: String) {
        gameName = name
    }
}
