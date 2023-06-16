package com.yapp.bol.presentation.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.GuestAddDialogBinding
import com.yapp.bol.presentation.utils.Converter.convertLengthToString
import com.yapp.bol.presentation.view.match.member_select.ValidateCallBack

class GuestAddDialog(
    private val context: Context,
    private val addGuest: (String) -> Unit,
    private val getValidateNickName: (String) -> Unit,
) : Dialog(context), ValidateCallBack {

    private lateinit var binding: GuestAddDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = GuestAddDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        context.dialogWidthResize(this, 1f)
        setGuestAddDescription()

        binding.btnGuestAdd.setOnClickListener {
            dismiss()
            addGuest(binding.etGuestName.text.toString())
        }

        binding.etGuestName.doOnTextChanged { text, start, _, count ->
            val color = if (count == 10) Color.parseColor("#EB5555") else Color.parseColor("#8B8B8B")
            binding.tvGuestNameCount.setTextColor(color)
            binding.tvGuestNameCount.text = convertLengthToString(PROFILE_NAME_MAX_LENGTH, start + count)
            getValidateNickName(text.toString())
        }
    }

    private fun setGuestAddDescription() {

        val description = context.resources.getString(R.string.guest_description_text)
        val spannableString = SpannableString(description)

        val firstStartIndex = FIRST_COLOR_CHANGE_START_POINT
        val firstEndIndex = FIRST_COLOR_CHANGE_END_POINT
        val secondStartIndex = SECOND_COLOR_CHANGE_START_POINT
        val secondEndIndex = SECOND_COLOR_CHANGE_END_POINT

        val orangeColor = ContextCompat.getColor(context, R.color.orange_09)
        val firstColorSpan = ForegroundColorSpan(orangeColor)
        val secondColorSpan = ForegroundColorSpan(orangeColor)
        spannableString.setSpan(
            firstColorSpan,
            firstStartIndex,
            firstEndIndex,
            SpannableString.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannableString.setSpan(
            secondColorSpan,
            secondStartIndex,
            secondEndIndex,
            SpannableString.SPAN_INCLUSIVE_INCLUSIVE
        )

        binding.tvDescription.text = spannableString
    }

    override fun setNicknameValid(value: Boolean) {
        binding.btnGuestAdd.isEnabled = value
    }

    companion object {
        const val PROFILE_NAME_MAX_LENGTH = 10
        const val FIRST_COLOR_CHANGE_START_POINT = 3
        const val FIRST_COLOR_CHANGE_END_POINT = 18
        const val SECOND_COLOR_CHANGE_START_POINT = 24
        const val SECOND_COLOR_CHANGE_END_POINT = 29
    }
}
