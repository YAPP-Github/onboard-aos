package com.yapp.bol.presentation.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.ViewGroup
import android.view.WindowManager
import com.yapp.bol.presentation.utils.Constant.RESULT_RECORD_DIALOG_HEIGHT

fun Context.dialogWidthResize(
    dialog: Dialog,
    widthRatio: Float,
    isResultDialog: Boolean = false,
) {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

    if (Build.VERSION.SDK_INT < 30) {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        val window = dialog.window
        val x = (size.x * widthRatio).toInt()
        val height  = if(isResultDialog) {
            this.convertPxToDp(RESULT_RECORD_DIALOG_HEIGHT)
        } else {
            ViewGroup.LayoutParams.WRAP_CONTENT
        }
        window?.setLayout(x, height)
    } else {
        val rect = windowManager.currentWindowMetrics.bounds

        val window = dialog.window
        val x = (rect.width() * widthRatio).toInt()

        val height  = if(isResultDialog) {
            this.convertPxToDp(RESULT_RECORD_DIALOG_HEIGHT)
        } else {
            ViewGroup.LayoutParams.WRAP_CONTENT
        }
        window?.setLayout(x, height)
    }
}

fun Context.convertPxToDp(px: Int): Int {
    val density = resources.displayMetrics.density
    return (px * density).toInt()
}
