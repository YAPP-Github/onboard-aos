package com.yapp.bol.presentation.view.match.game_select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.domain.usecase.login.MatchUseCase
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameSelectViewModel @Inject constructor(
    private val matchUseCase: MatchUseCase
) : ViewModel() {

    private val _gameList = MutableLiveData(listOf<GameItem>())
    val gameList: LiveData<List<GameItem>> = _gameList

    init {
        viewModelScope.launch {
            getGameList()
        }
    }
    private suspend fun getGameList() {
        matchUseCase.getGameList(9999).collectLatest {
            checkedApiResult(
                apiResult = it,
                success = { data -> _gameList.value = data },
            )
        }
    }
}
