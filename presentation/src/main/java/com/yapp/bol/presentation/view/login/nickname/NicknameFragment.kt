package com.yapp.bol.presentation.view.login.nickname

import android.content.Intent
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentNicknameBinding
import com.yapp.bol.presentation.utils.Converter
import com.yapp.bol.presentation.view.group.search.GroupSearchActivityTest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NicknameFragment : BaseFragment<FragmentNicknameBinding>(R.layout.fragment_nickname) {

    private val nicknameViewModel: NicknameViewModel by viewModels()

    override fun onViewCreatedAction(): Unit = with(binding) {
        btnSignUp.setOnClickListener {
            val intent = Intent(requireActivity(), GroupSearchActivityTest::class.java)
            startActivity(intent)
        }

        etUserName.doOnTextChanged { _, start, _, count ->
            tvUserNameCount.text =
                Converter.convertLengthToString(10, start + count)
            btnSignUp.isEnabled = (start + count) > 0
        }

        btnSignUp.setOnClickListener {
            nicknameViewModel.putUserName(etUserName.text.toString())
            moveGroupSearch()
        }
    }
    private fun moveGroupSearch() {
        val intent = Intent(requireActivity(), GroupSearchActivityTest::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}
