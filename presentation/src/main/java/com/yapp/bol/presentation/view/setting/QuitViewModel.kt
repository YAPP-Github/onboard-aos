package com.yapp.bol.presentation.view.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.user.UserItem
import com.yapp.bol.domain.usecase.login.GetMyInfoUseCase
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuitViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
) : ViewModel() {

    private val _userUiState = MutableStateFlow<SettingUiState<UserItem>>(SettingUiState.Loading)
    val userUiState: StateFlow<SettingUiState<UserItem>> = _userUiState

    private var id: Int = 0
    private var nickName = ""

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        _userUiState.value = SettingUiState.Loading

        viewModelScope.launch {
            getMyInfoUseCase.invoke().collectLatest { apiResult ->
                checkedApiResult(
                    apiResult = apiResult,
                    success = { data ->
                        id = data.id
                        nickName = data.nickname
                        _userUiState.value = SettingUiState.Success(data)
                    },
                    error = { _userUiState.value = SettingUiState.Error(IllegalStateException(it.message)) }
                )
            }
        }
    }

    fun getId(): Int = id

    fun getNickName(): String = nickName
}
