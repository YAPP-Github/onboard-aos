package com.yapp.bol.presentation.view.login

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentMainBinding
import com.yapp.bol.presentation.view.group.GroupActivity
import com.yapp.bol.presentation.view.login.auth.GoogleTestActivity
import com.yapp.bol.presentation.view.login.auth.KakaoTestActivity
import com.yapp.bol.presentation.view.login.auth.NaverTestActivity
import com.yapp.bol.presentation.view.login.dialog.TermsDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val loginViewModel: LoginViewModel by viewModels()

    private val dialog by lazy {
        TermsDialog(
            context = requireContext(),
            onClickTermsListener = object : TermsDialog.OnClickTermsListener {
                override fun onClickLike(position: Int, isChecked: Boolean) {
                    loginViewModel.updateLike(position, isChecked)
                }

                override fun onClickLikeAll(isChecked: Boolean) {
                    loginViewModel.updateAllLike(isChecked)
                }

                override fun onClickSignUp() {
                    if (loginViewModel.onboardState.value?.size == NONE_AGREE) moveSingUp()
                    else moveGroupSearch()
                }

                override fun onClickTermsDetail(url: String) {
                    moveTermsDetail(url)
                }

                override fun checkedTermsAll(state: Boolean): Boolean {
                    return loginViewModel.checkedTermsAll(state)
                }

                override fun dismissAction(state: Boolean) {
                    loginViewModel.updateDialogState(state)
                }
            }
        )
    }

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()

        loginViewModel.getAccessToken()
        setButtonListener()
        subscribeObservables()
    }

    private fun subscribeObservables() = with(loginViewModel) {
        onboardState.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            when (it.size) {
                ALL_AGREE -> moveGroupSearch()
                PARTIAL_AGREE -> checkedBoardPage(it[0])
                NONE_AGREE -> loginViewModel.getTerms()
            }
        }

        termsList.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            dialog.termsAdapter.submitList(it)
        }

        dialogState.observe(viewLifecycleOwner) {
            if (it.not()) return@observe
            dialog.show()
        }

        isEnableSignUp.observe(viewLifecycleOwner) {
            if (dialog.isShowing.not()) return@observe
            dialog.updateSignUpEnabled(it)
        }

        accessToken.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) return@observe
            loginViewModel.getOnBoard()
        }
    }

    private fun setButtonListener() {
        binding.btnGoogle.setOnClickListener {
            Intent(requireActivity(), GoogleTestActivity::class.java).also { startActivity(it) }
        }

        binding.btnKakao.setOnClickListener {
            Intent(requireActivity(), KakaoTestActivity::class.java).also { startActivity(it) }
        }

        binding.btnNaver.setOnClickListener {
            Intent(requireActivity(), NaverTestActivity::class.java).also { startActivity(it) }
        }
    }

    private fun checkedBoardPage(onBoard: String) {
        when (onBoard) {
            ONBOARD_TERMS -> loginViewModel.getTerms()
            ONBOARD_NICKNAME -> moveSingUp()
        }
    }

    private fun moveSingUp() {
        loginViewModel.postTerms()
        findNavController().navigate(R.id.action_mainFragment_to_signUpNicknameFragment)
    }

    private fun moveGroupSearch() {
        loginViewModel.postTerms()
        val intent = Intent(requireActivity(), GroupActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun moveTermsDetail(url: String) {
        val intent = Intent(requireActivity(), TermsWebViewActivity::class.java).apply {
            putExtra(WEB_VIEW_KEY, url)
        }
        startActivity(intent)
    }

    companion object {
        const val ONBOARD_TERMS = "TERMS"
        const val ONBOARD_NICKNAME = "NICKNAME"
        const val ALL_AGREE = 0
        const val PARTIAL_AGREE = 1
        const val NONE_AGREE = 2
        const val WEB_VIEW_KEY = "web view"
    }
}
