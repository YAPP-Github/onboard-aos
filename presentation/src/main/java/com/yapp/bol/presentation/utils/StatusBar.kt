package com.yapp.bol.presentation.utils

import android.app.Activity
import android.view.WindowManager
import androidx.core.view.WindowInsetsControllerCompat


fun setStatusBarColor(activity: Activity, color: Int) {
    activity.window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = color
        WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = false
    }
}