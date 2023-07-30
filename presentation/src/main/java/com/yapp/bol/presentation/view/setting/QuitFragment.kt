package com.yapp.bol.presentation.view.setting

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentQuitBinding
import com.yapp.bol.presentation.utils.collectWithLifecycle
import com.yapp.bol.presentation.utils.showToast
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
            val email = Intent(Intent.ACTION_SEND)
            email.type = "plain/text"
            val address = arrayOf("onboardaos2@gmail.com")
            email.putExtra(Intent.EXTRA_EMAIL, address)
            email.putExtra(Intent.EXTRA_SUBJECT, "제목")
            email.putExtra(Intent.EXTRA_TEXT, "id: ${viewModel.getId()}\n닉네임: ${viewModel.getNickName()}")
            startActivity(email)
        }
    }

    private fun observeQuitUiState() {
        viewModel.userUiState.collectWithLifecycle(this) { uiState ->
            when (uiState) {
                is SettingUiState.Loading -> {
                    binding.btnQuit.disableButton()
                }

                is SettingUiState.Success -> {
                    binding.btnQuit.enableButton()
                }

                is SettingUiState.Error -> {
                    binding.btnQuit.disableButton()
                    requireContext().showToast("현재 요청하신 작업을 수행할 수 없습니다. 다시 시도해 주십시오.")
                }
            }
        }
    }
}
