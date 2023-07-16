package com.yapp.bol.presentation.utils

import android.animation.ValueAnimator
import android.view.View
import androidx.core.animation.doOnEnd

fun View.createScaleAnimator(
    startPx: Int,
    endPx: Int,
    durationMS: Long = 200L,
    onFinish: () -> Unit,
): ValueAnimator {
    return ValueAnimator.ofInt(startPx, endPx).also { anim ->
        anim.duration = durationMS
        val lp = this.layoutParams
        anim.addUpdateListener {
            lp.apply {
                width = it.animatedValue as Int
                height = it.animatedValue as Int
            }.also { this.layoutParams = lp }
        }
        anim.doOnEnd { onFinish.invoke() }
    }
}
