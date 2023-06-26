package com.yapp.bol.designsystem.ui.button

import android.content.Context
import android.graphics.drawable.StateListDrawable
import androidx.core.content.ContextCompat.getDrawable
import com.yapp.bol.designsystem.R
import android.R as androidR

/**
 * selector를 state에 따라 만들어주는 함수입니다.
 */
fun getRoundedButtonDrawableState(
    context: Context,
    type: ButtonType,
    color: ButtonColor,
    duration: Int,
): StateListDrawable {

    val defaultShape: Int = when(color) {
        ButtonColor.Orange -> {
            when(type) {
                ButtonType.BottomRoundedSquareType -> R.drawable.bg_bottom_rounded_btn_orange10
                ButtonType.RoundedSquareType -> R.drawable.bg_rounded_square_btn_orange10
            }
        }
        ButtonColor.Transparent -> {
            when(type) {
                ButtonType.BottomRoundedSquareType -> R.drawable.bg_bottom_rounded_btn_transparent
                ButtonType.RoundedSquareType -> R.drawable.bg_rounded_square_btn_transparent
            }
        }
    }

    val pressedShape: Int = when(color) {
        ButtonColor.Orange -> {
            when(type) {
                ButtonType.BottomRoundedSquareType -> R.drawable.bg_bottom_rounded_btn_gray15
                ButtonType.RoundedSquareType -> R.drawable.bg_rounded_square_btn_gray15
            }
        }
        ButtonColor.Transparent -> {
            when(type) {
                ButtonType.BottomRoundedSquareType -> R.drawable.bg_bottom_rounded_btn_gray5
                ButtonType.RoundedSquareType -> R.drawable.bg_rounded_square_btn_gray5
            }
        }
    }

    return StateListDrawable().apply {
        addState(
            intArrayOf(androidR.attr.state_pressed),
            getDrawable(context, pressedShape)
        )
        addState(
            intArrayOf(-androidR.attr.state_pressed),
            getDrawable(context, defaultShape)
        )
        setEnterFadeDuration(duration)
        setExitFadeDuration(duration)
    }
}

/**
 * selector를 state에 따라 만들어주는 함수입니다.
 */
fun getSquareButtonDrawableState(
    context: Context,
    duration: Int,
): StateListDrawable {
    val defaultShape: Int = R.drawable.bg_square_btn_orange10
    val pressedShape: Int = R.drawable.bg_square_btn_gray15

    return StateListDrawable().apply {
        addState(
            intArrayOf(androidR.attr.state_pressed),
            getDrawable(context, pressedShape)
        )
        addState(
            intArrayOf(-androidR.attr.state_pressed),
            getDrawable(context, defaultShape)
        )
        setEnterFadeDuration(duration)
        setExitFadeDuration(duration)
    }
}
