package com.yapp.bol.presentation.view.group.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.yapp.bol.designsystem.R
import com.yapp.bol.presentation.databinding.ProfileSettingDialogBinding
import com.yapp.bol.presentation.utils.Converter.convertLengthToString
import com.yapp.bol.presentation.utils.dialogWidthResize
import com.yapp.bol.presentation.utils.isNicknameValid

class ProfileSettingDialog(
    private val context: Context,
    private val userName: String,
    private val createGroup: (String) -> Unit,
) : Dialog(context) {

    private lateinit var binding: ProfileSettingDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ProfileSettingDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        context.dialogWidthResize(this, 0.9f)

        binding.btnProfileComplete.setOnClickListener {
            dismiss()
            val name = if (binding.etProfileName.text.isNullOrEmpty()) {
                userName
            } else {
                binding.etProfileName.text.toString()
            }
            createGroup(name)
        }

        binding.etProfileName.apply {
            hint = userName
            doOnTextChanged { text, start, _, count ->
                setNicknameCount(start, count)
                setNicknameValid(text.toString())
            }
        }
    }

    private fun setNicknameCount(start: Int, count: Int) {
        val color = if (count != 10) R.color.Gray_8 else R.color.Red
        binding.tvProfileNameCount.setTextColor(ContextCompat.getColor(context, color))
        binding.tvProfileNameCount.text = convertLengthToString(PROFILE_NAME_MAX_LENGTH, start + count)
    }

    private fun setNicknameValid(nickname: String) {
        val value = isNicknameValid(nickname)
        binding.btnProfileComplete.isEnabled = value
        val color = if (value) R.color.Gray_8 else R.color.Red
        binding.tvNicknameSettingGuide.setTextColor(ContextCompat.getColor(context, color))
    }

    override fun show() {
        super.show()
        binding.etProfileName.requestFocus()
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    companion object {
        const val PROFILE_NAME_MAX_LENGTH = 10
    }
}
