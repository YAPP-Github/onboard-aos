package com.yapp.bol.designsystem.ui.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.yapp.bol.designsystem.R
import com.yapp.bol.designsystem.databinding.BolSquareButtonBinding
import com.yapp.bol.designsystem.ui.button.BolButtonConfig.animationDuration

class BolSquareButton constructor(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(context, attrs), BolBaseButton {

    private val binding: BolSquareButtonBinding by lazy {
        BolSquareButtonBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        binding
        getAttrsValue(context, attrs)
    }

    private var buttonText: String? = null

    private fun getAttrsValue(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BolSquareButton)

        try {
            buttonText = typedArray.getString(R.styleable.BolSquareButton_squareButtonText)
            setEnableButton()
            typedArray.recycle()
        } catch (_: Exception) {
            throw IllegalArgumentException()
        }
    }

    private fun setEnableButton() {
        binding.tvBtn.apply {
            text = buttonText
            setTextColor(getEnableTextColor())
        }
        binding.bgBtn.background = getSquareButtonDrawableState(context, animationDuration)
    }

    private fun getEnableTextColor(): Int =
        ContextCompat.getColor(binding.root.context, R.color.Gray_1)

    private fun getDisableTextColor(): Int =
        ContextCompat.getColor(binding.root.context, R.color.Gray_7)


    /**
     * BolSquareButton 비활성화 함수
     */
    override fun disableButton() {
        if (!binding.bgBtn.isEnabled) {
            return
        }

        binding.tvBtn.apply {
            setTextColor(getDisableTextColor())
        }

        binding.bgBtn.apply {
            isEnabled = false
            background = AppCompatResources.getDrawable(context, R.drawable.bg_square_btn_disable)
        }
    }

    /**
     * BolSquareButton 클릭 시 수행할 작업 구현하는 함수
     * @param onClick type: () -> Unit
     */
    override fun setOnClickListener(onClick: () -> Unit) {
        binding.bgBtn.setOnClickListener {
            onClick()
        }
    }

    /**
     * BolSquareButton 활성화 함수
     */
    override fun enableButton() {
        if (binding.bgBtn.isEnabled) {
            return
        }

        binding.bgBtn.isEnabled = true
        setEnableButton()
    }
}
