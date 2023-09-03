package com.yapp.bol.presentation.view.setting

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentQuitBinding
import com.yapp.bol.presentation.firebase.GA
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
            val string = binding.root.resources.getString(R.string.quit_email_content)
            val content = String.format(string, viewModel.getId(), viewModel.getNickName())
            email.apply {
                type = "plain/text"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("onboardaos2@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "온보드 회원 탈퇴 신청")
                putExtra(Intent.EXTRA_TEXT, content)
            }.also { startActivity(it) }
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

    override fun getScreenName(): String = GA.Screen.QUIZ
}
