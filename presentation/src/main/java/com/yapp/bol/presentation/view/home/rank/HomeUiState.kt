package com.yapp.bol.presentation.view.home.rank

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Error(val exception: Throwable) : HomeUiState()
    object Success : HomeUiState()
}
