package com.yapp.bol.presentation.view.match

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yapp.bol.presentation.utils.Constant.GAME_RESULT_RECORD
import javax.inject.Inject

class MatchViewModel @Inject constructor() : ViewModel() {

    private val _toolBarTitle = MutableLiveData(GAME_RESULT_RECORD)
    val toolBarTitle = _toolBarTitle

    fun updateToolBarTitle(title: String) {
        _toolBarTitle.value = title
    }
}
