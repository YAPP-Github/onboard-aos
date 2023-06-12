package com.yapp.bol.presentation.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.yapp.bol.presentation.databinding.ProfileSettingDialogBinding
import com.yapp.bol.presentation.utils.Converter.convertLengthToString


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

        binding.etProfileName.doOnTextChanged { text, start, before, count ->
            val color = if (count == 10) Color.parseColor("#EB5555") else Color.parseColor("#8B8B8B")
            binding.tvProfileNameCount.setTextColor(color)
            binding.tvProfileNameCount.text = convertLengthToString(PROFILE_NAME_MAX_LENGTH, start + count)
        }
    }

    companion object {
        const val PROFILE_NAME_MAX_LENGTH = 10
    }
}
