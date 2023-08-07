package com.yapp.bol.presentation.view.setting

sealed class SettingUiState<out T>(val _data: T?) {
    object Loading : SettingUiState<Nothing>(_data = null)
    data class Error(val error: Throwable) : SettingUiState<Nothing>(_data = null)
    data class Success<out R>(val data: R) : SettingUiState<R>(_data = data)
}
