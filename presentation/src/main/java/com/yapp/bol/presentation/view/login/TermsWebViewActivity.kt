package com.yapp.bol.presentation.view.login

import android.util.Log
import android.webkit.WebViewClient
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivityTermsWebViewBinding
import com.yapp.bol.presentation.view.login.LoginFragment.Companion.WEB_VIEW_KEY

class TermsWebViewActivity : BaseActivity<ActivityTermsWebViewBinding>(R.layout.activity_terms_web_view) {

    override fun onCreateAction() {
        with(binding) {
            wvTerms.settings.apply {
                javaScriptEnabled = true
            }

            wvTerms.apply {
                webViewClient = WebViewClient()
                val url = intent?.getStringExtra(WEB_VIEW_KEY) ?: ""
                loadUrl(url)
            }

            ibBackButton.setOnClickListener {
                finish()
            }
        }
    }
}
