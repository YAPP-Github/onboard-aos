package com.yapp.bol.presentation.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@SuppressLint("CheckResult")
fun ImageView.loadRoundImage(url: String, roundedCorners: Int) {
    Glide.with(this)
        .load(url)
        .transform(CenterCrop(), RoundedCorners(context.dpToPx(roundedCorners)))
        .into(this)
}

fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .transform(CenterCrop())
        .into(this)
}
