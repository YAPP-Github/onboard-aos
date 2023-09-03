package com.yapp.bol.presentation.view.setting

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentTermBinding
import com.yapp.bol.presentation.firebase.GA
import com.yapp.bol.presentation.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermFragment : BaseFragment<FragmentTermBinding>(R.layout.fragment_term) {

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()

        val isPrivacyTerm: Boolean = arguments?.getBoolean(TERMS_BUNDLE_KEY) ?: kotlin.run {
            true
        }
        val termUrl: String = arguments?.getString(TERMS_URL) ?: kotlin.run {
            requireContext().showToast("잠시 후 다시 시도해주세요.")
            ""
        }

        setTitle(isPrivacyTerm)
        setBackButton()
        setWebView(termUrl)
    }

    private fun setTitle(isPrivacyTerm: Boolean) {
        binding.tvTitle.text = if (isPrivacyTerm) {
            binding.root.context.getString(R.string.setting_terms_privacy)
        } else { binding.root.context.getString(R.string.setting_terms_service) }
    }

    private fun setBackButton() {
        binding.btnBack.setOnClickListener {
            binding.root.findNavController().popBackStack()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView(url: String) {
        binding.webViewTerm.apply {
            settings.javaScriptEnabled = true
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClientClass()
            loadUrl(url)
        }
    }

    private class WebViewClientClass : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Log.d("check URL", url)
            view.loadUrl(url)
            return true
        }
    }

    override fun getScreenName(): String = GA.Screen.TERMS

    companion object {
        const val TERMS_BUNDLE_KEY = "IS_PRIVACY_TERM"
        const val TERMS_URL = "URL_TERM"

        fun startFragment(
            navController: NavController,
            isPrivacy: Boolean,
            termUrl: String
        ) {
            val bundle: Bundle = Bundle().apply {
                putBoolean(TERMS_BUNDLE_KEY, isPrivacy)
                putString(TERMS_URL, termUrl)
            }
            navController.navigate(R.id.action_settingFragment_to_termFragment, bundle)
        }
    }
}
