package com.yapp.bol.presentation.utils

import android.widget.ImageView
import com.yapp.bol.designsystem.R
import com.yapp.bol.presentation.view.match.MatchActivity

fun ImageView.setDiceImageForRole(role: String) {
    val imageRes = if (role == MatchActivity.GUEST) {
        setPadding(10, 10, 10, 10)
        R.drawable.img_dice_empty
    } else {
        setPadding(0, 0, 0, 0)
        R.drawable.img_dice
    }
    setImageResource(imageRes)
}
