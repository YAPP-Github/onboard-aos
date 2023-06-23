package com.yapp.bol.presentation.view.match

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yapp.bol.domain.usecase.login.MatchUseCase
import com.yapp.bol.presentation.utils.Constant.GAME_RESULT_RECORD
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val matchUseCase: MatchUseCase
) : ViewModel() {

    private val _toolBarTitle = MutableLiveData(GAME_RESULT_RECORD)
    val toolBarTitle: LiveData<String> = _toolBarTitle

    fun updateToolBarTitle(title: String) {
        _toolBarTitle.value = title
    }
}
