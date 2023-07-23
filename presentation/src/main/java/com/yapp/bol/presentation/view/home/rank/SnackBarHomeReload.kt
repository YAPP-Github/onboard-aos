package com.yapp.bol.presentation.view.home.rank

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.SnackbarHomeReloadBinding

class SnackBarHomeReload(
    view: View,
    private val onClick: () -> Unit,
) {
    companion object {
        fun make(
            view: View,
            onClick: () -> Unit,
        ) = SnackBarHomeReload(view, onClick)
    }

    private val context = view.context
    private val snackbar = Snackbar.make(view, "", Int.MAX_VALUE)
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    private val snackbarBinding: SnackbarHomeReloadBinding =
        DataBindingUtil.inflate(inflater, R.layout.snackbar_home_reload, null, false)

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackbarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, 0)
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackbarBinding.root, 0)
        }
    }

    private fun initData() {
        snackbarBinding.btnReload.setOnClickListener {
            onClick.invoke()
        }
    }

    fun show() {
        snackbar.show()
    }

    fun dismiss() {
        snackbar.dismiss()
    }
}
