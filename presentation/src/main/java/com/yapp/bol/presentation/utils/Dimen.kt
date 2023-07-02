package com.yapp.bol.presentation.utils

import android.content.Context
import androidx.annotation.DimenRes

fun Context.getDimen(@DimenRes dimenResId: Int): Float {
    return resources.getDimension(dimenResId)
}
