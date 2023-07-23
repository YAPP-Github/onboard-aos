package com.yapp.bol.presentation.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

fun String.copyToClipboard(context: Context) {
    val clipboardManager = context.getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("code", this)
    clipboardManager.setPrimaryClip(clipData)
}
