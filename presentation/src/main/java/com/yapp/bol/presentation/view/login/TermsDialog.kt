package com.yapp.bol.presentation.view.login

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import com.yapp.bol.presentation.databinding.TermsDialogBinding
import com.yapp.bol.presentation.utils.dialogWidthResize

class TermsDialog(
    private val context: Context,
    private val onClickListener: OnClickTermsListener,
) : Dialog(context) {

    interface OnClickTermsListener {
        fun onClickService(state: Boolean): Boolean
        fun onClickPrivacy(state: Boolean): Boolean
        fun onClickMarketing(state: Boolean)
        fun onClickAll(state: Boolean): Boolean
        fun moveSingUpNickname()
    }

    private lateinit var binding: TermsDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = TermsDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        context.dialogWidthResize(this@TermsDialog, 1f, true)
        window?.setGravity(Gravity.BOTTOM)
        setOnClickListener()
    }

    private fun setOnClickListener() = with(binding) {
        cbService.setOnClickListener {
            btnSignUp.isEnabled = onClickListener.onClickService(cbService.isChecked)
        }

        cbPrivacy.setOnClickListener {
            btnSignUp.isEnabled = onClickListener.onClickPrivacy(cbPrivacy.isChecked)
        }
        cbMarketing.setOnClickListener {
            onClickListener.onClickMarketing(cbPrivacy.isChecked)
        }

        cbAll.setOnClickListener {
            cbService.isChecked = cbAll.isChecked
            cbPrivacy.isChecked = cbAll.isChecked
            cbMarketing.isChecked = cbAll.isChecked
            btnSignUp.isEnabled = onClickListener.onClickAll(cbAll.isChecked)
        }

        btnSignUp.setOnClickListener {
            onClickListener.moveSingUpNickname()
            dismiss()
        }
    }
}
