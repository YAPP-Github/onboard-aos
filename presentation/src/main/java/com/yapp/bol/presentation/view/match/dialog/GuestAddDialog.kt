package com.yapp.bol.presentation.view.match.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.google.android.gms.common.api.Scope
import com.yapp.bol.designsystem.R
import com.yapp.bol.presentation.databinding.GuestAddDialogBinding
import com.yapp.bol.presentation.utils.Converter.convertLengthToString
import com.yapp.bol.presentation.utils.dialogWidthResize
import com.yapp.bol.presentation.utils.textChangesToFlow
import com.yapp.bol.presentation.view.match.member_select.ValidateCallBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class GuestAddDialog(
    private val context: Context,
    private val addGuest: (String) -> Unit,
    private val scope: CoroutineScope,
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
            addGuest(binding.etGuestName.text.toString())
            dismiss()
        }

        scope.launch {
            val ediTextFlow = binding.etGuestName.textChangesToFlow()
            val debounceDuration = 300L

            ediTextFlow
                .debounce(debounceDuration)
                .onEach {
                    val keyword = it.toString()
                    getValidateNickName(keyword)
                }
                .launchIn(this)
        }


        binding.etGuestName.doOnTextChanged { _, start, _, count ->
            val color = if (count == 10) R.color.Red else R.color.Gray_8
            binding.tvGuestNameCount.setTextColor(ContextCompat.getColor(context, color))
            binding.tvGuestNameCount.text = convertLengthToString(PROFILE_NAME_MAX_LENGTH, start + count)
        }

        this.window?.setGravity(Gravity.BOTTOM)
    }

    override fun show() {
        super.show()
        binding.etGuestName.requestFocus()
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    override fun dismiss() {
        binding.etGuestName.text = null
        binding.btnGuestAdd.isEnabled = false
        super.dismiss()
    }

    private fun setGuestAddDescription() {

        val description = context.resources.getString(R.string.guest_description_text)
        val spannableString = SpannableString(description)

        val firstStartIndex = FIRST_COLOR_CHANGE_START_POINT
        val firstEndIndex = FIRST_COLOR_CHANGE_END_POINT
        val secondStartIndex = SECOND_COLOR_CHANGE_START_POINT
        val secondEndIndex = SECOND_COLOR_CHANGE_END_POINT

        val orangeColor = ContextCompat.getColor(context, R.color.Orange_9)
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
        if (binding.etGuestName.text.isNotEmpty()) binding.tvDuplicationGuide.isVisible = value.not()
        else binding.tvDuplicationGuide.isVisible = false
    }

    companion object {
        const val PROFILE_NAME_MAX_LENGTH = 10
        const val FIRST_COLOR_CHANGE_START_POINT = 3
        const val FIRST_COLOR_CHANGE_END_POINT = 18
        const val SECOND_COLOR_CHANGE_START_POINT = 24
        const val SECOND_COLOR_CHANGE_END_POINT = 29
    }
}
