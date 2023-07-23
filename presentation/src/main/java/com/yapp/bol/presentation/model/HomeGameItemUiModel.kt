package com.yapp.bol.presentation.model

sealed class HomeGameItemUiModel {
    data class GameItem(val item: GameItemWithSelected) : HomeGameItemUiModel()
    object Padding : HomeGameItemUiModel()
}
