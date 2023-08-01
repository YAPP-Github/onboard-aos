package com.yapp.bol.presentation.utils

import android.app.Activity
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat

fun setStatusBarColor(
    activity: Activity,
    @ColorRes color: Int,
    isIconBlack: Boolean,
) {
    activity.window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = ContextCompat.getColor(activity, color)
        WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = isIconBlack
    }
}

fun setNavigationBarColor(
    activity: Activity,
    @ColorRes color: Int,
) {
    activity.window.apply {
        navigationBarColor = ContextCompat.getColor(activity, color)
    }
}
