package com.yapp.bol.designsystem.ui.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.yapp.bol.designsystem.R
import com.yapp.bol.designsystem.databinding.BolRoundedButtonBinding
import com.yapp.bol.designsystem.ui.button.BolButtonConfig.animationDuration

class BolRoundedButton : ConstraintLayout {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        getAttrsValue(context, attrs)
    }

    private val binding: BolRoundedButtonBinding by lazy {
        BolRoundedButtonBinding.inflate(LayoutInflater.from(context), this, true)
    }
    private var buttonType: ButtonType = ButtonType.RoundedSquareType
    private var buttonColor: ButtonColor = ButtonColor.Orange
    private var buttonText: String? = null

    private fun getAttrsValue(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BolRoundedButton)

        try {
            buttonType = ButtonType.fromParams(typedArray.getInt(
                R.styleable.BolRoundedButton_bolButtonType, ButtonType.RoundedSquareType.ordinal
            ))
            buttonColor = ButtonColor.fromParams(typedArray.getInt(
                R.styleable.BolRoundedButton_bolButtonColor, ButtonColor.Orange.ordinal
            ))
            buttonText = typedArray.getString(R.styleable.BolRoundedButton_bolButtonText)

            setEnableButton()
            typedArray.recycle()
        } catch (_: Exception) {
            throw IllegalArgumentException()
        }
    }

    private fun setEnableButton() {
        binding.btnBol.apply {
            text = buttonText
            setTextColor(getEnableTextColor(buttonColor))
            background = getRoundedButtonDrawableState(
                context = binding.root.context,
                type = buttonType,
                color = buttonColor,
                duration = animationDuration,
            )
        }
    }

    private fun getEnableTextColor(color: ButtonColor): Int =
        when (color) {
            ButtonColor.Orange -> ContextCompat.getColor(binding.root.context, R.color.Gray_2)
            ButtonColor.Transparent -> ContextCompat.getColor(binding.root.context, R.color.Gray_10)
        }

    private fun getDisableTextColor(): Int =
        ContextCompat.getColor(binding.root.context, R.color.Gray_7)

    fun disableButton() {
        if (!binding.btnBol.isEnabled) {
            return
        }

        binding.btnBol.apply {
            isEnabled = false
            background = when (buttonType) {
                ButtonType.RoundedSquareType -> AppCompatResources.getDrawable(
                    context,
                    R.drawable.bg_rounded_square_btn_disable
                )
                ButtonType.BottomRoundedSquareType -> AppCompatResources.getDrawable(
                    context,
                    R.drawable.bg_bottom_rounded_btn_disable
                )
            }
            setTextColor(getDisableTextColor())
        }
    }

    fun setOnClickListener(onClick: () -> Unit) {
        binding.btnBol.setOnClickListener {
            onClick()
        }
    }

    fun enableButton() {
        if (binding.btnBol.isEnabled) {
            return
        }

        binding.btnBol.isEnabled = true
        setEnableButton()
    }
}
