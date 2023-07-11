package com.yapp.bol.presentation.view.group.join

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.DialogInputBinding
import com.yapp.bol.presentation.utils.Keyboard
import com.yapp.bol.presentation.utils.dpToPx
import com.yapp.bol.presentation.utils.inflate

class InputDialog(
    context: Context,
) : Dialog(context) {

    private val binding: DialogInputBinding by lazy {
        context.inflate(R.layout.dialog_input, null, false)
    }

    private var onLimitExceeded: ((String, InputDialog) -> Unit)? = null
    private var onLimit: Int = 0
    private var inputText = ""

    init {
        setContentView(binding.root)

        window?.apply {
            setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE, // ktlint-disable max-line-length
            )

            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
            )
            attributes?.y = context.dpToPx(100)
        }
        binding.root.updateLayoutParams<MarginLayoutParams> {
            leftMargin = context.dpToPx(18)
            rightMargin = context.dpToPx(18)
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPress()

        binding.etInput.requestFocus()
        binding.etInput.postDelayed({ Keyboard.open(context, binding.etInput) }, 220)

        binding.tvInputCount.text = "${binding.etInput.text.length}/$onLimit"

        binding.etInput.doAfterTextChanged { text ->

            binding.tvInputCount.text = "${text?.length ?: 0}/$onLimit"
            binding.tvSummit.isEnabled = true

            if ((text?.length ?: 0) > onLimit) {
                onLimitExceeded?.invoke(text.toString(), this)
                text?.substring(0, onLimit).let {
                    binding.etInput.setText(it)
                    inputText = it.toString()
                }
                binding.etInput.setSelection(onLimit)

                binding.tvInputCount.setTextColor(context.getColor(R.color.Red))
            } else {
                binding.tvInputCount.setTextColor(context.getColor(R.color.Gray_8))
            }
        }
    }

    fun setOnSummit(onSummit: (String, InputDialog) -> Unit): InputDialog {
        binding.etLayout.updatePadding(bottom = 0)
        binding.etInput.setText(listOf("이승은", "차경민", "기본 이름", "랜덤 이름").random()) // todo 기획이 완료 된 후 실제 기본 이름으로 변경해야합니다.

        binding.tvSummit.setOnClickListener {
            onSummit(inputText, this)
        }
        return this
    }

    fun setTitle(title: CharSequence): InputDialog {
        binding.tvTitle.text = title
        return this
    }

    fun setHintText(hint: CharSequence): InputDialog {
        binding.etInput.hint = hint
        return this
    }

    fun visibleSummitButton(visible: Boolean): InputDialog {
        binding.tvSummit.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    fun visibleInputCount(visible: Boolean): InputDialog {
        binding.tvInputCount.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    fun setTitle(@StringRes title: Int?): InputDialog {
        binding.tvTitle.text = context.getString(title ?: return this)
        return this
    }

    fun setMessage(message: CharSequence): InputDialog {
        binding.tvMessage.text = message
        return this
    }

    fun setMessage(@StringRes message: Int): InputDialog {
        binding.tvMessage.text = context.getString(message)
        return this
    }

    fun setLimitSize(limit: Int): InputDialog {
        this.onLimit = limit
        return this
    }

    fun setOnLimit(onLimitExceeded: (String, InputDialog) -> Unit): InputDialog {
        this.onLimitExceeded = onLimitExceeded
        return this
    }

    fun showErrorMessage(message: String) {
        binding.tvErrorMessage.visibility = View.VISIBLE
        binding.tvErrorMessage.text = message

        binding.tvSummit.isEnabled = false
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
