package com.yapp.bol.presentation.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@SuppressLint("CheckResult")
fun ImageView.loadImage(imageUri: String, roundedCorners: Int = 20) {
    Glide.with(this)
        .load(imageUri)
        .apply {
            if (roundedCorners > 0) {
                transform(CenterCrop(), RoundedCorners(context.dpToPx(roundedCorners)))
            } else {
                transform(CenterCrop())
            }
        }
        .into(this)
}
