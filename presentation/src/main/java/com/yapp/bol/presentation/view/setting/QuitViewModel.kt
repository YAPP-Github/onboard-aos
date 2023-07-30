package com.yapp.bol.presentation.view.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.usecase.auth.DeleteAccessTokenUseCase
import com.yapp.bol.domain.usecase.auth.DeleteRefreshTokenUseCase
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuitViewModel : ViewModel() {

    private val _quitStateFlow = MutableStateFlow<SettingUiState<Boolean>>(SettingUiState.Success(false))
    val quitStateFlow: StateFlow<SettingUiState<Boolean>> = _quitStateFlow

    fun quitAccount() {
        _quitStateFlow.value = SettingUiState.Loading

        viewModelScope.launch {

        }
    }
}
