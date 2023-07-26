package com.yapp.bol.presentation.view.setting

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentSettingBinding
import com.yapp.bol.presentation.utils.config.SettingConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    override fun onViewCreatedAction() {
        super.onViewCreatedAction()
        setNavigateButton()
        setBackButton()
    }

    private fun setNavigateButton() {
        binding.apply {
            btnQuit.setOnClickListener {
                findNavController().navigate(R.id.action_settingFragment_to_quitFragment)
            }
            btnTermsPrivacy.setOnClickListener {
                val isPrivacyTerm = true
                val bundle: Bundle = Bundle().apply {
                    putBoolean(SettingConfig.TERMS_BUNDLE_KEY, isPrivacyTerm)
                }
                findNavController().navigate(R.id.action_settingFragment_to_termFragment, bundle)
            }
            btnTermsService.setOnClickListener {
                val isPrivacyTerm = false
                val bundle: Bundle = Bundle().apply {
                    putBoolean(SettingConfig.TERMS_BUNDLE_KEY, isPrivacyTerm)
                }
                findNavController().navigate(R.id.action_settingFragment_to_termFragment, bundle)
            }
            btnOpenSource.setOnClickListener {
                startActivity(Intent(requireContext(), OssLicensesMenuActivity::class.java))
            }
        }
    }

    private fun setBackButton() {
        binding.btnBack.setOnClickListener {
            binding.root.findNavController().popBackStack()
        }
    }
}
