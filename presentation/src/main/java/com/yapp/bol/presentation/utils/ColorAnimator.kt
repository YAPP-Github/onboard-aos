package com.yapp.bol.presentation.utils

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView

/**
 * + startColor: anim 시작 색상
 * + endColor: anim 종료 색상
 * + durationMS: anim 지속 milliseconds
 *
 * 뷰의 background의 tint가 시작 색상으로부터 종료 색상까지 자연스럽게 이어지는 애니메이션
 * durationMS 값이 작을수록 애니메이션의 자연스러움 정도는 떨어짐
 */
fun View.createSmoothColorAnimator(
    startColor: Int,
    endColor: Int,
    durationMS: Long = 1000L,
): ValueAnimator {
    return ValueAnimator.ofArgb(startColor, endColor).also { anim ->
        anim.duration = durationMS
        anim.repeatMode = ValueAnimator.REVERSE
        anim.addUpdateListener {
            this.backgroundTintList = ColorStateList.valueOf(it.animatedValue as Int)
        }
    }
}

/**
 * + startColor: anim 시작 색상
 * + endColor: anim 종료 색상
 * + durationMS: anim 지속 milliseconds
 *
 * 텍스트뷰의 text color가 시작 색상으로부터 종료 색상까지 자연스럽게 이어지는 애니메이션
 * 시작 색상으로부터 종료 색상까지 자연스럽게 이어지는 애니메이션
 * durationMS 값이 작을수록 애니메이션의 자연스러움 정도는 떨어짐
 */
fun TextView.createSmoothTextColorAnimator(
    startColor: Int,
    endColor: Int,
    durationMS: Long = 1000L,
): ValueAnimator {
    return ValueAnimator.ofArgb(startColor, endColor).also { anim ->
        anim.duration = durationMS
        anim.repeatMode = ValueAnimator.REVERSE
        anim.addUpdateListener {
            this.setTextColor(it.animatedValue as Int)
        }
    }
}
