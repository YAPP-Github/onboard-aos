package com.yapp.bol.presentation.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object Keyboard {

    fun open(
        context: Context,
        editText: EditText,
        option: Int = InputMethodManager.SHOW_FORCED,
    ): Boolean {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        return imm.showSoftInput(editText, option)
    }

    fun close(
        context: Context,
        view: View,
        option: Int = 0,
    ): Boolean {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        return imm.hideSoftInputFromWindow(view.windowToken, option)
    }
}
