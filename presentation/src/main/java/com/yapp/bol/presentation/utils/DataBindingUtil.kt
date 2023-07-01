package com.yapp.bol.presentation.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil

fun <T> ViewGroup.inflate(
    @LayoutRes layoutRes: Int,
    attachToRoot: Boolean = false,
): T {
    return DataBindingUtil.inflate(LayoutInflater.from(this.context), layoutRes, this, attachToRoot)
}
