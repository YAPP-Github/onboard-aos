package com.yapp.bol.presentation.view.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentSettingBinding
import com.yapp.bol.presentation.utils.collectWithLifecycle
import com.yapp.bol.presentation.utils.config.SettingConfig
import com.yapp.bol.presentation.utils.navigateFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val termViewModel: TermViewModel by viewModels()
    private var privacyUrl: String =""
    private var serviceUrl: String =""

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()
        setNavigateButton()
        setBackButton()
        subscribeObservables()
    }

    private fun subscribeObservables() {
        termViewModel.termStateFlow.collectWithLifecycle(this) {
            privacyUrl = it.privacy
            serviceUrl = it.service
        }
    }

    private fun setNavigateButton() {
        binding.apply {
            btnQuit.setOnClickListener {
                findNavController().navigateFragment(R.id.action_settingFragment_to_quitFragment)
            }
            btnTermsPrivacy.setOnClickListener {
                TermFragment.startFragment(
                    findNavController(),
                    isPrivacy = true,
                    termUrl = privacyUrl,
                )
            }
            btnTermsService.setOnClickListener {
                TermFragment.startFragment(
                    findNavController(),
                    isPrivacy = false,
                    termUrl = serviceUrl,
                )
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
