package com.yapp.bol.presentation.utils

import android.animation.ValueAnimator
import android.view.View
import androidx.core.animation.doOnEnd

/**
 * + startPx: anim 시작 px
 * + endPx: anim 종료 px (destination)
 * + durationMS: anim 지속 milliseconds
 * + @nullable onFinish: anim 종료 시 실행되어야 하는 작업
 *
 * 뷰의 크기를 확대하거나 축소하는데 사용할 수 있는 애니메이터를 생성
 */
fun View.createScaleAnimator(
    startPx: Int,
    endPx: Int,
    durationMS: Long = 200L,
    onFinish: (() -> Unit)?,
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
        anim.doOnEnd { onFinish?.invoke() }
    }
}
