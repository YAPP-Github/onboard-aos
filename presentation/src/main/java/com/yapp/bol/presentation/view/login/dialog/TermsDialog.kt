package com.yapp.bol.presentation.view.login.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import com.yapp.bol.domain.model.TermsItem
import com.yapp.bol.presentation.databinding.TermsDialogBinding
import com.yapp.bol.presentation.utils.dialogWidthResize

class TermsDialog(
    private val context: Context,
    private val onClickTermsListener: OnClickTermsListener
) : Dialog(context) {

    interface OnClickTermsListener {
        fun onClickLike(position: Int, isChecked: Boolean)
        fun onClickLikeAll(isChecked: Boolean)
        fun dismissAction(state: Boolean)
        fun onClickSignUp()
        fun onClickTermsDetail(url: String)
        fun checkedTermsAll(state: Boolean): Boolean
    }

    private lateinit var binding: TermsDialogBinding

    val termsAdapter by lazy {
        TermsAdapter(
            object : TermsAdapter.OnClickItemListener {
                override fun onClickLike(position: Int, isChecked: Boolean) {
                    onClickTermsListener.onClickLike(position,isChecked)
                }

                override fun onClickTermsDetail(url: String) {
                    onClickTermsListener.onClickTermsDetail(url)
                }

                override fun checkedTermsAll(state: Boolean): Boolean {
                    return onClickTermsListener.checkedTermsAll(state)
                }

                override fun updateTermsAll(state: Boolean) {
                    binding.cbAll.isChecked = state
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = TermsDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        context.dialogWidthResize(this@TermsDialog, 1f, true)
        window?.setGravity(Gravity.BOTTOM)

        binding.rvTermsList.adapter = termsAdapter
        setOnClickListener()
    }

    private fun setOnClickListener() = with(binding) {
        btnSignUp.setOnClickListener {
            onClickTermsListener.onClickSignUp()
            dismiss()
        }

        cbAll.setOnClickListener {
            onClickTermsListener.onClickLikeAll(cbAll.isChecked)
        }
    }

    fun updateSignUpEnabled(enabled: Boolean) {
        binding.btnSignUp.isEnabled = enabled
    }

    override fun dismiss() {
        onClickTermsListener.dismissAction(false)
        super.dismiss()
    }
}
