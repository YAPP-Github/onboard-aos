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

class ProfileSettingDialog(
    private val context: Context,
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
            createGroup(binding.etProfileName.text.toString())
        }

        binding.etProfileName.doOnTextChanged { _, start, _, count ->
            val color = if (count == 10) R.color.Orange_10 else R.color.Gray_8
            binding.tvProfileNameCount.setTextColor(ContextCompat.getColor(context, color))
            binding.tvProfileNameCount.text = convertLengthToString(PROFILE_NAME_MAX_LENGTH, start + count)
        }
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
