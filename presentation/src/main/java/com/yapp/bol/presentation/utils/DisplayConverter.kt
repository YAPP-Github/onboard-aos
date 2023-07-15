package com.yapp.bol.presentation.utils

import android.content.Context

fun Context.dpToPx(dp: Float): Int {
    val metrics = resources.displayMetrics
    val px = dp * (metrics.densityDpi / 160f)

    return px.toInt()
}

fun Context.dpToPx(dp: Int): Int {
    val metrics = resources.displayMetrics
    val px = dp * (metrics.densityDpi / 160f)

    return px.toInt()
}

fun Context.pxToDp(px: Int): Int {
    val metrics = resources.displayMetrics
    val dp = px / (metrics.densityDpi / 160f)

    return dp.toInt()
}
