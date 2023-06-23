package com.yapp.bol.presentation.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class KeyboardManager(
    private val activity: Activity
) {
    private val inputManager
        = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    fun hideKeyboard() {
        if (activity.currentFocus == null) return
        inputManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun showKeyboard(editText: EditText) {
        if (activity.currentFocus == null) return
        inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}
