package com.yapp.bol.presentation.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.yapp.bol.presentation.databinding.ImageSettingDialogBinding

class ImageSettingDialog(
    private val context: Context,
    private val checkedGalleryAccess: () -> Unit,
) : Dialog(context) {

    private lateinit var binding: ImageSettingDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ImageSettingDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        context.dialogWidthResize(this, 0.9f)

        binding.btnMoveGallery.setOnClickListener {
            dismiss()
            checkedGalleryAccess()
        }
    }
}
