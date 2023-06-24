package com.yapp.bol.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VD : ViewDataBinding>(@LayoutRes val layoutRes: Int) : AppCompatActivity() {

    val binding: VD by lazy {
        DataBindingUtil.setContentView(this, layoutRes) as VD
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        initViewModel(viewModel)
        onCreateAction()
    }

    override fun onStart() {
        super.onStart()
        onStartAction()
    }

    override fun onResume() {
        super.onResume()
        onResumeAction()
    }

    protected open fun onCreateAction() {}
    protected open fun onStartAction() {}
    protected open fun onResumeAction() {}
}
