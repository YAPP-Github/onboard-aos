package com.yapp.bol.presentation.model

import com.yapp.bol.domain.model.GameItem

data class GameItemWithSelected(
    val gameItem: GameItem,
    val isSelected: Boolean,
)
