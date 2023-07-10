package com.yapp.bol.presentation.view.login

import android.content.Intent
import androidx.core.widget.doOnTextChanged
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentNicknameBinding
import com.yapp.bol.presentation.utils.Converter
import com.yapp.bol.presentation.view.group.NewGroupActivity
import com.yapp.bol.presentation.view.group.search.GroupSearchActivityTest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NicknameFragment : BaseFragment<FragmentNicknameBinding>(R.layout.fragment_nickname) {

    override fun onViewCreatedAction(): Unit = with(binding) {
        btnSignUp.setOnClickListener {
            val intent = Intent(requireActivity(), GroupSearchActivityTest::class.java)
            startActivity(intent)
        }

        etUserName.doOnTextChanged { _, start, _, count ->
            tvUserNameCount.text =
                Converter.convertLengthToString(NewGroupActivity.DESCRIPTION_MAX_LENGTH, start + count)
            btnSignUp.isEnabled = (start + count) > 0
        }
    }
}
