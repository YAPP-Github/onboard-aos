package com.yapp.bol.presentation.utils

import android.graphics.Rect
import android.view.Window

object Device {

    fun getHeight(window: Window): Int {
        val rect = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rect)
        return rect.height()
    }
}
