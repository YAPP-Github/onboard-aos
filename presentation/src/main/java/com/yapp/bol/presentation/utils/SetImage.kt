package com.yapp.bol.presentation.utils

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun ImageView.setImage(imageUri: String) {
    Glide.with(this)
        .load(imageUri)
        .transform(CenterCrop(), RoundedCorners(20))
        .into(this)
}
