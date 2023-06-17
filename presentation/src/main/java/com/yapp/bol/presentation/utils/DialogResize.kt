package com.yapp.bol.presentation.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.ViewGroup
import android.view.WindowManager

fun Context.dialogWidthResize(dialog: Dialog, width: Float) {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

    if (Build.VERSION.SDK_INT < 30) {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        val window = dialog.window
        val x = (size.x * width).toInt()

        window?.setLayout(x, ViewGroup.LayoutParams.WRAP_CONTENT)

    } else {
        val rect = windowManager.currentWindowMetrics.bounds

        val window = dialog.window
        val x = (rect.width() * width).toInt()

        window?.setLayout(x, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
