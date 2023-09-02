package com.yapp.bol.presentation.view.group.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.FragmentGroupJoinBinding
import com.yapp.bol.presentation.utils.collectWithLifecycle
import com.yapp.bol.presentation.utils.dpToPx
import com.yapp.bol.presentation.utils.loadImage
import com.yapp.bol.presentation.utils.setStatusBarColor
import com.yapp.bol.presentation.view.group.GroupActivity
import com.yapp.bol.presentation.view.group.join.data.Margin
import com.yapp.bol.presentation.view.group.join.type.GroupResultType
import com.yapp.bol.presentation.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull

@AndroidEntryPoint
class GroupJoinFragment : Fragment() {

    private lateinit var binding: FragmentGroupJoinBinding
    private val viewModel by viewModels<GroupJoinViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(requireActivity(), R.color.Gray_15, false)
    }

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
    }

    private fun initView() {
        binding.tvGroupJoin.setOnClickListener {
            if (viewModel.isAlreadyJoinGroup()) {
                moveHomeActivity()
            } else {
                showRedeemInputDialog()
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun showRedeemInputDialog() {
        InputDialog(requireContext()).apply {
            this.setTitle("참여 코드 입력")
                .setMessage(getString(R.string.group_join_code_input_plz))
                .setLimitSize(6)
                .setSingleLine(true)
                .onBackPressed {
                    it.dismiss()
                }
                .setTitleIcon(
                    // todo: 경민님 체크 필요 ic_clock 제거하고 디자인 시스템 쪽 아이콘으로 바꿔주었습니다.
                    // 문제가 없다면 주석 제거 부탁드립니다.
                    icon = R.drawable.ic_time_line16,
                    size = context.dpToPx(20),
                    margin = Margin(rightMargin = context.dpToPx(8)),
                )
                .setOnLimit { code, dialog ->
                    viewModel.checkGroupJoinByAccessCode(code)

                    viewModel.groupResult.collectWithLifecycle(viewLifecycleOwner) { groupResultType ->
                        when (groupResultType) {
                            is GroupResultType.LOADING -> {
                                showLoading(true, groupResultType.message)
                            }

                            is GroupResultType.SUCCESS -> {
                                showLoading(false)

                                showProfileSettingDialog(dialog, code)
                                dismiss()
                            }

                            is GroupResultType.ValidationAccessCode -> {
                                showLoading(false)

                                dialog.showErrorMessage(groupResultType.message)
                            }

                            is GroupResultType.UnknownError -> {
                                showLoading(false)

                                dialog.showErrorMessage(groupResultType.message)
                            }

                            else -> {}
                        }
                    }
                }.show()
        }
    }

    private fun showLoading(isLoading: Boolean, message: String? = null) {
        binding.loadingLayout.isVisible = isLoading
        binding.tvLoadingTitle.text = message
    }

    private fun showProfileSettingDialog(dialog: InputDialog, code: String) {
        InputDialog(requireContext())
            .setTitle("프로필 설정")
            .setMessage("모임에서 사용할 닉네임을 10자 이하로 입력해주세요.")
            .setLimitSize(10)
            .setSingleLine(true)
            .setText(viewModel.nickName)
            .setHintText("닉네임을 입력해주세요.")
            .visibleInputCount(true)
            .visibleSummitButton(true)
            .onBackPressed {
                it.dismiss()
                dialog.dismiss()
            }
            .setOnSummit { nickname, nickNameDialog ->
                viewModel.joinGroup(code, nickname)
                viewModel.groupResult.collectWithLifecycle(viewLifecycleOwner) { groupResultType ->
                    when (groupResultType) {
                        is GroupResultType.LOADING -> {
                            showLoading(true, groupResultType.message)
                        }

                        is GroupResultType.SUCCESS -> {
                            dialog.dismiss()
                            nickNameDialog.dismiss()

                            WelcomeJoinDialog(requireContext(), nickname).apply {
                                setOnDismissListener {
                                    moveHomeActivity()
                                }
                            }.show()
                        }

                        is GroupResultType.UnknownError -> {
                            showLoading(false)

                            dialog.showErrorMessage(groupResultType.message)
                        }

                        is GroupResultType.ValidationNickname -> {
                            showLoading(false)

                            dialog.showErrorMessage(groupResultType.message)
                        }

                        else -> {}
                    }
                }
            }.show()
    }

    private fun moveHomeActivity() {
        when (activity) {
            is GroupActivity -> {
                HomeActivity.startActivity(binding.root.context, groupId = viewModel.groupItem.value!!.groupDetail.id)
                requireActivity().finish()
            }

            is HomeActivity -> {
                findNavController().navigate(R.id.action_groupJoinFragment_to_homeRankFragment)
            }
        }
    }

    private fun subscribeObservables() {
        with(viewModel) {
            groupItem.filterNotNull().collectWithLifecycle(viewLifecycleOwner) {
                binding.groupAdminView.setGroupItemDetailTitle(it.groupDetail.ownerNickname)
                binding.groupMemberView.setGroupItemDetailTitle("${it.groupDetail.memberCount}명")
                binding.ivGroupJoinBg.loadImage(it.groupDetail.profileImageUrl, 0)
            }
        }
    }
}
