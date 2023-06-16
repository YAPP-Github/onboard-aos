package com.yapp.bol.presentation.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun ImageView.loadImage(imageUri: String) {
    Glide.with(this)
        .load(imageUri)
        .transform(CenterCrop(), RoundedCorners(20))
        .into(this)
}
