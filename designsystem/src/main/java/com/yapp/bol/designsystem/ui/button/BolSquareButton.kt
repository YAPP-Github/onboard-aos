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

class BolSquareButton : ConstraintLayout {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        getAttrsValue(context, attrs)
    }

    private val binding: BolSquareButtonBinding by lazy {
        BolSquareButtonBinding.inflate(LayoutInflater.from(context), this, true)
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
        binding.btnBol.apply {
            text = buttonText
            setTextColor(getEnableTextColor())
            background = getSquareButtonDrawableState(context, animationDuration)
        }
    }

    private fun getEnableTextColor(): Int =
        ContextCompat.getColor(binding.root.context, R.color.Gray_1)

    private fun getDisableTextColor(): Int =
        ContextCompat.getColor(binding.root.context, R.color.Gray_7)

    fun disableButton() {
        if (!binding.btnBol.isEnabled) {
            return
        }

        binding.btnBol.apply {
            isEnabled = false
            background = AppCompatResources.getDrawable(context, R.drawable.bg_square_btn_disable)
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
