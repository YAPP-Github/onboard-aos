package com.yapp.bol.designsystem.ui.button

enum class ButtonType() {
    RoundedSquareType,
    BottomRoundedSquareType;

    companion object {
        fun fromParams(id: Int): ButtonType {
            return when(id) {
                RoundedSquareType.ordinal -> RoundedSquareType
                BottomRoundedSquareType.ordinal -> BottomRoundedSquareType
                else -> throw IllegalArgumentException()
            }
        }
    }
}

enum class ButtonColor() {
    Orange,
    Transparent;

    companion object {
        fun fromParams(id: Int): ButtonColor {
            return when(id) {
                Orange.ordinal -> Orange
                Transparent.ordinal -> Transparent
                else -> throw IllegalArgumentException()
            }
        }
    }
}

object BolButtonConfig {
    const val animationDuration: Int = 200
}
