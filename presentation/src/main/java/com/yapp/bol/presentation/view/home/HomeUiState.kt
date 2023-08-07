package com.yapp.bol.presentation.view.home

sealed class HomeUiState<out T>(val _data: T?) {
    object Loading : HomeUiState<Nothing>(_data = null)
    data class Error(val error: Throwable) : HomeUiState<Nothing>(_data = null)
    data class Success<out R>(val data: R) : HomeUiState<R>(_data = data)
}
