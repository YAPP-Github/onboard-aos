package com.yapp.bol.presentation.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

/**
 * debounce 기능
 * ref. https://shorturl.at/knJR0
 */
fun EditText.textChangesToFlow(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(text)
            }
        }
        addTextChangedListener(listener)
        awaitClose {
            removeTextChangedListener(listener)
        }
    }.onStart {
        emit(text)
    }
}

/**
 * ImeOption을 EditText에 준 경우 그 옵션 값을 사용자가 키보드에서 눌렀을 떄
 * 키보드가 사라지고 EditText의 focus도 사라지게 하는 Util 함수
 */
fun EditText.loseFocusOnAction(
    actionId: Int,
    context: Context,
) {
    this.setOnEditorActionListener { _, id, _ ->
        if (id == actionId) {
            this@loseFocusOnAction.clearFocus()

            // TODO : 경민님 코드의 키보드 유틸로 바꾸어주어야 함.
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.windowToken, 0)
        }
        true
    }
}
