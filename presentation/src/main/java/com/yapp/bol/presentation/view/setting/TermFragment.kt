package com.yapp.bol.presentation.view.setting

import androidx.navigation.findNavController
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentTermBinding
import com.yapp.bol.presentation.utils.config.SettingConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermFragment : BaseFragment<FragmentTermBinding>(R.layout.fragment_term) {

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()

        val isPrivacyTerm: Boolean = arguments?.getBoolean(SettingConfig.TERMS_BUNDLE_KEY) ?: kotlin.run {
            true
        }

        setTitle(isPrivacyTerm)
        setBackButton()
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
}
