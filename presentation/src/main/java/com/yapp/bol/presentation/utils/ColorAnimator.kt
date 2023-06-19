package com.yapp.bol.presentation.utils

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView

fun View.colorAnimator(
    middleColor: Int,
    endColor: Int,
    durationMS: Long = 1000L,
): ValueAnimator {
    return ValueAnimator.ofArgb(middleColor, endColor).also { anim ->
        anim.duration = durationMS
        anim.repeatMode = ValueAnimator.REVERSE
        anim.addUpdateListener {
            this.backgroundTintList = ColorStateList.valueOf(it.animatedValue as Int)
        }
    }
}

fun TextView.textColorAnimator(
    middleColor: Int,
    endColor: Int,
    durationMS: Long = 1000L,
): ValueAnimator {
    return ValueAnimator.ofArgb(middleColor, endColor).also { anim ->
        anim.duration = durationMS
        anim.repeatMode = ValueAnimator.REVERSE
        anim.addUpdateListener {
            this.setTextColor(it.animatedValue as Int)
        }
    }
}
