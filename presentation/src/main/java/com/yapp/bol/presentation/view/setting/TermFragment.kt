package com.yapp.bol.presentation.view.setting

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentTermBinding
import com.yapp.bol.presentation.utils.collectWithLifecycle
import com.yapp.bol.presentation.utils.config.SettingConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermFragment : BaseFragment<FragmentTermBinding>(R.layout.fragment_term) {

    private val viewModel: TermViewModel by viewModels()

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()

        val isPrivacyTerm: Boolean = arguments?.getBoolean(SettingConfig.TERMS_BUNDLE_KEY) ?: kotlin.run {
            true
        }

        setTitle(isPrivacyTerm)
        setBackButton()
        viewModel.getTerms()
        subscribeObservables()
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

    private fun subscribeObservables() {
        viewModel.termStateFlow.collectWithLifecycle(this) {
            // TODO : 약관 관련 처리
        }
    }
}
