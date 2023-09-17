package com.yapp.bol.presentation.view.group.join

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.DialogInputBinding
import com.yapp.bol.presentation.utils.Constant.NICKNAME_REGEX
import com.yapp.bol.presentation.utils.Keyboard
import com.yapp.bol.presentation.utils.dpToPx
import com.yapp.bol.presentation.utils.inflate
import com.yapp.bol.presentation.view.group.join.data.Margin

class InputDialog(
    context: Context,
) : Dialog(context) {

    private val binding: DialogInputBinding by lazy {
        context.inflate(R.layout.dialog_input, null, false)
    }

    private var onLimitExceeded: ((String, InputDialog) -> Unit)? = null
    private var onTyping: ((String, InputDialog) -> Unit)? = null
    private var onBackPressed: ((InputDialog) -> Unit)? = null
    private var onLimit: Int = 0

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
            setGravity(Gravity.BOTTOM)
        }
        binding.root.updateLayoutParams<MarginLayoutParams> {
            leftMargin = context.dpToPx(18)
            rightMargin = context.dpToPx(18)
            bottomMargin = context.dpToPx(36)
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.etInput.requestFocus()
        binding.etInput.postDelayed({ Keyboard.open(context, binding.etInput) }, 220)

        binding.tvInputCount.text = "${binding.etInput.text.length}/$onLimit"

        binding.etInput.filters = arrayOf(InputFilter.LengthFilter(onLimit))

        binding.etInput.doAfterTextChanged { text ->
            onTyping?.invoke(text.toString(), this)

            binding.tvInputCount.text = "${text?.length ?: 0}/$onLimit"
            binding.tvSummit.isEnabled = !text.isNullOrEmpty()
            binding.tvSummit.setTextColor(
                if (text.isNullOrEmpty()) {
                    getColor(context, R.color.Gray_7)
                } else {
                    getColor(context, R.color.Gray_1)
                },
            )
            binding.tvErrorMessage.visibility = View.INVISIBLE

            if ((text?.count()) == onLimit) {
                text.substring(0, onLimit).let {
                    onLimitExceeded?.invoke(it, this)
                }
                binding.tvInputCount.setTextColor(context.getColor(R.color.Red))
            } else {
                binding.tvInputCount.setTextColor(context.getColor(R.color.Gray_8))
            }
            if (text?.matches(Regex(NICKNAME_REGEX)) != true) {
                binding.tvErrorMessage.visibility = View.VISIBLE
                binding.tvErrorMessage.text = "한글, 영문, 숫자를 조합하여 사용 가능합니다."
                binding.tvErrorMessage.setTextColor(context.getColor(R.color.Gray_8))
            }
        }
    }

    fun setOnSummit(onSummit: (String, InputDialog) -> Unit): InputDialog {
        binding.tvSummit.setOnClickListener {
            onSummit(binding.etInput.text.toString(), this)
        }
        return this
    }

    fun setOnTyping(onTyping: (String, InputDialog) -> Unit): InputDialog {
        this.onTyping = onTyping
        return this
    }

    fun setText(text: String): InputDialog {
        binding.etLayout.updatePadding(bottom = 0)
        binding.etInput.setText(text)
        return this
    }

    fun onBackPressed(onBackPressed: (dialog: InputDialog) -> Unit): InputDialog {
        this.onBackPressed = onBackPressed
        return this
    }

    fun setTitle(title: CharSequence): InputDialog {
        binding.tvTitle.text = title
        return this
    }

    fun setHintText(@StringRes hintId: Int): InputDialog {
        binding.etInput.setHint(hintId)
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

    fun visibleGuestMember(visible: Boolean): InputDialog {
        binding.llGuestMember.isVisible = visible
        return this
    }

    fun setMessage(@StringRes messageId: Int): InputDialog {
        binding.tvMessage.text = context.getString(messageId)
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

    fun setSingleLine(isSingleLine: Boolean): InputDialog {
        binding.etInput.isSingleLine = isSingleLine
        return this
    }

    fun showErrorMessage(message: String) {
        binding.tvErrorMessage.visibility = View.VISIBLE
        binding.tvErrorMessage.text = message

        binding.tvSummit.isEnabled = false
        binding.tvSummit.setTextColor(getColor(context, R.color.Gray_7))
    }

    fun setTitleIcon(@DrawableRes icon: Int, size: Int, margin: Margin): InputDialog {
        binding.ivTitleIcon.setBackgroundResource(icon)
        binding.ivTitleIcon.updateLayoutParams<MarginLayoutParams> {
            width = size
            height = size

            updateMargins(
                top = margin.topMargin,
                left = margin.leftMargin,
                right = margin.rightMargin,
                bottom = margin.bottomMargin,
            )
        }
        return this
    }

    fun setGuestOnClicked(click: () -> Unit): InputDialog {
        binding.llGuestMember.setOnClickListener { click() }
        return this
    }

    override fun onBackPressed() {
        super.onBackPressed()
        onBackPressed?.invoke(this)
    }
}
