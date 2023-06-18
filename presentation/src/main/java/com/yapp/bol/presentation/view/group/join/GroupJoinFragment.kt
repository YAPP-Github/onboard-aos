package com.yapp.bol.presentation.view.group.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.FragmentGroupJoinBinding

class GroupJoinFragment : Fragment() {

    private lateinit var binding: FragmentGroupJoinBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentGroupJoinBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.tvGroupJoin.setOnClickListener {
            showRedeemInputDialog()
        }
    }

    private fun showRedeemInputDialog() {
        InputDialog(requireContext())
            .setTitle("참여 코드 입력")
            .setMessage(getString(R.string.group_join_code_input_plz))
            .setLimitSize(6)
            .setOnLimit {
                showProfileSettingDialog()
            }.show()
    }

    private fun showProfileSettingDialog() {
        InputDialog(requireContext())
            .setTitle("프로필 설정")
            .setMessage("모임에서 사용할 닉네임을 10자 이하로 입력해주세요.\n")
            .setLimitSize(10)
            .setOnSummit {
                // todo request server api
            }.show()
    }
}
