package com.yapp.bol.presentation.view.setting

import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentTermBinding

class TermFragment : BaseFragment<FragmentTermBinding>(R.layout.fragment_term) {

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()

        setTitle(false)
    }

    private fun setTitle(isPrivacyTerm: Boolean) {
        binding.tvTitle.text = if (isPrivacyTerm) {
            binding.root.context.getString(R.string.setting_terms_privacy)
        } else { binding.root.context.getString(R.string.setting_terms_service) }
    }
}
