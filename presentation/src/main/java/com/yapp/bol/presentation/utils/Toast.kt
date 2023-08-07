package com.yapp.bol.presentation.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(@StringRes resId: Int, length: Int = Toast.LENGTH_SHORT):
    Toast = showToast(getString(resId), length)

fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT): Toast =
    Toast.makeText(this, message, length).also { it.show() }
