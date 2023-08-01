package com.yapp.bol.presentation.view.login.nickname

import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentNicknameBinding
import com.yapp.bol.presentation.utils.Converter
import com.yapp.bol.presentation.utils.isNicknameValid
import com.yapp.bol.presentation.view.group.GroupActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NicknameFragment : BaseFragment<FragmentNicknameBinding>(R.layout.fragment_nickname) {

    private val nicknameViewModel: NicknameViewModel by viewModels()

    override fun onViewCreatedAction(): Unit = with(binding) {
        btnSignUp.setOnClickListener {
            val intent = Intent(requireActivity(), GroupActivity::class.java)
            startActivity(intent)
        }

        etUserName.doOnTextChanged { text, start, _, count ->
            setNicknameCount(start, count)
            setNicknameValid(text.toString(), (start + count) > 0)
        }

        btnSignUp.setOnClickListener {
            nicknameViewModel.putUserName(etUserName.text.toString())
            moveGroupSearch()
        }
    }

    private fun setNicknameCount(start: Int, count: Int) {
        val color = if (count != 10) com.yapp.bol.designsystem.R.color.Gray_8 else com.yapp.bol.designsystem.R.color.Red
        binding.tvUserNameCount.setTextColor(ContextCompat.getColor(requireContext(), color))
        binding.tvUserNameCount.text = Converter.convertLengthToString(10, start + count)
    }

    private fun setNicknameValid(nickname: String, isCount: Boolean) {
        val value = isNicknameValid(nickname)
        binding.btnSignUp.isEnabled = isCount && value
        val color = if (value) com.yapp.bol.designsystem.R.color.Gray_8 else com.yapp.bol.designsystem.R.color.Red
        binding.tvNicknameSettingGuide.setTextColor(ContextCompat.getColor(requireContext(), color))
    }

    private fun moveGroupSearch() {
        val intent = Intent(requireActivity(), GroupActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}
