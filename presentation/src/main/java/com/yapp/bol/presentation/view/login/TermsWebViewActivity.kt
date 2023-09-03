package com.yapp.bol.presentation.view.login

import android.webkit.WebViewClient
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivityTermsWebViewBinding
import com.yapp.bol.presentation.firebase.GA
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

    override fun getScreenName(): String = GA.Screen.TERMS_WEB_VIEW
}
