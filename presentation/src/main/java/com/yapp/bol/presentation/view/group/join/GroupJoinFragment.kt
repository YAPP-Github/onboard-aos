package com.yapp.bol.presentation.view.group.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.FragmentGroupJoinBinding
import com.yapp.bol.presentation.utils.collectWithLifecycle
import com.yapp.bol.presentation.utils.dpToPx
import com.yapp.bol.presentation.view.group.join.data.Margin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupJoinFragment : Fragment() {

    private lateinit var binding: FragmentGroupJoinBinding
    private val viewModel by viewModels<GroupJoinViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentGroupJoinBinding.inflate(inflater, container, false).apply {
        binding = this
        vm = viewModel
        lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeObservables()
        viewModel.groupItem.collectWithLifecycle(viewLifecycleOwner) {
            binding.groupMemberView.setGroupItemDetailTitle("${it?.memberCount}명")
        }
    }

    private fun initView() {
        binding.tvGroupJoin.setOnClickListener {
            showRedeemInputDialog()
        }
    }

    private fun showRedeemInputDialog() {
        InputDialog(requireContext()).apply {
            this.setTitle("참여 코드 입력")
                .setMessage(getString(R.string.group_join_code_input_plz))
                .setLimitSize(6)
                .setSingleLine(true)
                .setTitleIcon(
                    icon = R.drawable.ic_lock,
                    size = context.dpToPx(20),
                    margin = Margin(rightMargin = context.dpToPx(8)),
                )
                .setOnLimit { code, dialog ->
                    viewModel.checkGroupJoinByAccessCode(code)

                    viewModel.successCheckGroupAccessCode.collectWithLifecycle(viewLifecycleOwner) { (success, message) -> // ktlint-disable max-line-length
                        if (success) {
                            showProfileSettingDialog(code)
                            dismiss()
                        } else {
                            dialog.showErrorMessage(message.orEmpty())
                        }
                    }
                }.show()
        }
    }

    private fun showProfileSettingDialog(code: String) {
        InputDialog(requireContext())
            .setTitle("프로필 설정")
            .setMessage("모임에서 사용할 닉네임을 10자 이하로 입력해주세요.")
            .setLimitSize(10)
            .setHintText("닉네임을 입력해주세요.")
            .visibleInputCount(true)
            .visibleSummitButton(true)
            .setOnSummit { nickname, dialog ->
                viewModel.joinGroup(code, nickname)

                viewModel.successJoinGroup.collectWithLifecycle(viewLifecycleOwner) { (success, message) ->
                    if (success) {
                        WelcomeJoinDialog(requireContext(), nickname).apply {
                            setOnDismissListener {
                                // todo 랭킹 화면으로 이동
                            }
                        }.show()
                        dialog.dismiss()
                    } else {
                        dialog.showErrorMessage(message.orEmpty())
                    }
                }
            }.show()
    }

    private fun subscribeObservables() {
        with(viewModel) {
            loading.collectWithLifecycle(viewLifecycleOwner) { (isLoading, message) ->
                binding.loadingLayout.isVisible = isLoading
                binding.tvLoadingTitle.text = message
            }
        }
    }
}
