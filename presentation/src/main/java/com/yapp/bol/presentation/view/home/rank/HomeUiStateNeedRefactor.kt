package com.yapp.bol.presentation.view.home.rank

sealed class HomeUiStateNeedRefactor {
    object Loading : HomeUiStateNeedRefactor()
    data class Error(val exception: Throwable) : HomeUiStateNeedRefactor()
    object Success : HomeUiStateNeedRefactor()
}
