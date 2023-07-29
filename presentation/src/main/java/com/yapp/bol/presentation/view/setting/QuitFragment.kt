package com.yapp.bol.presentation.view.setting

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentQuitBinding
import com.yapp.bol.presentation.utils.collectWithLifecycle
import com.yapp.bol.presentation.utils.showToast
import com.yapp.bol.presentation.view.login.SplashActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuitFragment : BaseFragment<FragmentQuitBinding>(R.layout.fragment_quit) {

    private val viewModel by viewModels<QuitViewModel>()

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()
        setButton()
        observeQuitUiState()
    }

    private fun setButton() {
        binding.btnBack.setOnClickListener {
            binding.root.findNavController().popBackStack()
        }

        binding.btnQuit.setOnClickListener {
            viewModel.quitAccount()
        }
    }

    private fun observeQuitUiState() {
        viewModel.quitStateFlow.collectWithLifecycle(this) { uiState ->
            when (uiState) {
                is SettingUiState.Loading -> {}
                is SettingUiState.Success -> {
                    if (uiState.data) {
                        startActivity(Intent(requireContext(), SplashActivity::class.java))
                        requireActivity().finish()
                    }
                }

                is SettingUiState.Error -> {
                    requireContext().showToast("회원 탈퇴가 처리되지 않았습니다. 다시 시도해 주십시오.")
                }
            }
        }
    }
}
