package com.yapp.bol.presentation.view.group.join

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.utils.Keyboard

class InputDialog(
    context: Context,
) : Dialog(context) {

    private var title: CharSequence? = null
    private var message: CharSequence? = null
    private var onLimitExceeded: ((String) -> Unit)? = null
    private var onLimitChanged: ((Int) -> Unit)? = null
    private var onLimit: Int = 0

    init {
        setContentView(R.layout.dialog_input)

        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
            )
            attributes?.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = findViewById<TextView>(R.id.tv_title)
        val message = findViewById<TextView>(R.id.tv_message)
        val input = findViewById<EditText>(R.id.et_input)

        title.text = this.title
        message.text = this.message
        onBackPress()

        input.requestFocus()

        input.doAfterTextChanged { text ->
            onLimitChanged?.invoke(text?.length ?: 0)

            if ((text?.length ?: 0) > onLimit) {
                onLimitExceeded?.invoke(text.toString())
                input.setText(text?.substring(0, onLimit))
                input.setSelection(onLimit)
            }
        }

        Keyboard.open(context, input, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    fun setTitle(title: CharSequence): InputDialog {
        this.title = title
        return this
    }

    fun setTitle(@StringRes title: Int?): InputDialog {
        this.title = context.getString(title ?: return this)
        return this
    }

    fun setMessage(message: CharSequence): InputDialog {
        this.message = message
        return this
    }

    fun setMessage(@StringRes message: Int): InputDialog {
        this.message = context.getString(message)
        return this
    }

    fun setOnLimitChanged(onLimitChanged: (Int) -> Unit): InputDialog {
        this.onLimitChanged = onLimitChanged
        return this
    }

    fun setLimitSize(limit: Int): InputDialog {
        this.onLimit = limit
        return this
    }

    fun setOnLimit(onLimitExceeded: (String) -> Unit): InputDialog {
        this.onLimitExceeded = onLimitExceeded
        return this
    }

    private fun onBackPress() {
        setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                dismiss()
                Keyboard.close(context, currentFocus ?: return@setOnKeyListener true)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }
}
