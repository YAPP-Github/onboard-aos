package com.yapp.bol.presentation.view.login

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.Constants.ACCESS_TOKEN
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentMainBinding
import com.yapp.bol.presentation.firebase.analysis.GA
import com.yapp.bol.presentation.utils.Constant
import com.yapp.bol.presentation.view.login.auth.GoogleTestActivity
import com.yapp.bol.presentation.view.login.auth.KakaoTestActivity
import com.yapp.bol.presentation.view.login.auth.NaverTestActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val loginViewModel: LoginViewModel by viewModels()

    private val dialog by lazy {
        TermsDialog(
            context = requireContext(),
            onClickListener = object : TermsDialog.OnClickTermsListener {
                override fun onClickService(state: Boolean): Boolean {
                    loginViewModel.updateTermsServiceState(state)
                    return loginViewModel.checkedRequired()
                }

                override fun onClickPrivacy(state: Boolean): Boolean {
                    loginViewModel.updateTermsPrivacyState(state)
                    return loginViewModel.checkedRequired()
                }

                override fun onClickMarketing(state: Boolean) {
                    loginViewModel.updateTermsMarketingState(state)
                }

                override fun onClickAll(state: Boolean): Boolean {
                    loginViewModel.updateTermsServiceState(state)
                    loginViewModel.updateTermsPrivacyState(state)
                    loginViewModel.updateTermsMarketingState(state)
                    return loginViewModel.checkedRequired()
                }

                override fun moveSingUpNickname() {
                    moveSingUpFragment()
                }
            }
        )
    }

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()
        val accessToken = arguments?.getString(ACCESS_TOKEN) ?: Constant.EMPTY_STRING

        if (accessToken.isNotEmpty()) dialog.show()
        setButtonListener()
    }

    override fun getScreenName(): String = GA.Screen.LOGIN

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

    private fun moveSingUpFragment() {
        findNavController().navigate(R.id.action_mainFragment_to_signUpNicknameFragment)
    }
}
