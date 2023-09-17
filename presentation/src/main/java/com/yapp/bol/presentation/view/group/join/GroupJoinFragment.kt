package com.yapp.bol.presentation.view.group.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.FragmentGroupJoinBinding
import com.yapp.bol.presentation.utils.collectWithLifecycle
import com.yapp.bol.presentation.utils.dpToPx
import com.yapp.bol.presentation.utils.loadImage
import com.yapp.bol.presentation.utils.setStatusBarColor
import com.yapp.bol.presentation.view.group.GroupActivity
import com.yapp.bol.presentation.view.group.dialog.guest.GuestListDialog
import com.yapp.bol.presentation.view.group.join.data.Margin
import com.yapp.bol.presentation.view.group.join.type.GroupResultType
import com.yapp.bol.presentation.view.home.HomeActivity
import com.yapp.bol.presentation.view.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull

@AndroidEntryPoint
class GroupJoinFragment : Fragment() {

    private lateinit var binding: FragmentGroupJoinBinding
    private val viewModel by viewModels<GroupJoinViewModel>()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val profileSettingDialog by lazy { InputDialog(requireContext()) }
    private val redeemDialog by lazy { InputDialog(requireContext()) }

    private val guestListDialog by lazy {
        GuestListDialog(
            context = requireContext(),
            clearGuest = viewModel::cleatGuest,
            getNextGuest = { viewModel.getMembers() },
            joinedGroup = { guestId, nickname, guestDialog ->
                viewModel.joinGroup(viewModel.accessCode, nickname, guestId)
                subscribeObservableGroupResult(
                    onLoading = { errorMessageId ->
                        showLoading(true, getString(errorMessageId))
                    },
                    onSuccess = {
                        profileSettingDialog.dismiss()
                        guestDialog.dismiss()
                        handleSuccessJoinGroup()
                    },
                    onUnknownError = { errorMessage ->
                        showLoading(false)

                        redeemDialog.showErrorMessage(errorMessage)
                    },
                )
            },
        )
    }

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

    private fun initView() = binding.run {
        tvGroupJoin.setOnClickListener {
            if (viewModel.isAlreadyJoinGroup()) {
                moveHomeActivity()
            } else {
                showRedeemInputDialog()
            }
        }
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun showRedeemInputDialog() {
        redeemDialog.apply {
            this.setTitle("참여 코드 입력")
                .setMessage(R.string.group_join_code_input_plz)
                .setLimitSize(6)
                .setSingleLine(true)
                .onBackPressed {
                    it.dismiss()
                }
                .setTitleIcon(
                    icon = R.drawable.ic_lock,
                    size = context.dpToPx(20),
                    margin = Margin(rightMargin = context.dpToPx(8)),
                )
                .setOnLimit { code, dialog ->
                    viewModel.checkGroupJoinByAccessCode(code)

                    subscribeObservableGroupResult(
                        onLoading = { errorMessageId ->
                            showLoading(true, getString(errorMessageId))
                        },
                        onSuccess = {
                            showLoading(false)

                            viewModel.accessCode = code
                            showProfileSettingDialog(dialog, code)

                            dismiss()
                        },
                        onUnknownError = { errorMessage ->
                            showLoading(false)

                            dialog.showErrorMessage(errorMessage)
                        },
                        onValidationAccessCode = { errorMessageId ->
                            showLoading(false)

                            dialog.showErrorMessage((getString(errorMessageId)))
                        },
                    )
                }.show()
        }
    }

    private fun showLoading(isLoading: Boolean, message: String? = null) {
        binding.loadingLayout.isVisible = isLoading
        binding.tvLoadingTitle.text = message
    }

    private fun showProfileSettingDialog(dialog: InputDialog, code: String) {
        profileSettingDialog.apply {
            val previousDialogDismiss = {
                dialog.dismiss()
                dismiss()
            }
            InputDialog(requireContext()).apply {
                setTitle("프로필 설정")
                    .setMessage(R.string.group_join_nickname_modal_message)
                    .setLimitSize(10)
                    .setSingleLine(true)
                    .setText(viewModel.nickName)
                    .setHintText(R.string.group_join_nickname_hint_title)
                    .visibleInputCount(true)
                    .visibleGuestMember(true)
                    .visibleSummitButton(true)
                    .setGuestOnClicked {
                        viewModel.getMembers()
                    }
                    .onBackPressed {
                        previousDialogDismiss()
                    }
                    .setOnTyping { nickname, dialog ->
                        if (nickname.isNotEmpty()) viewModel.validateNickName(nickname)

                        subscribeObservableGroupResult(
                            onValidationNickname = { errorMessageId ->
                                showLoading(false)

                                dialog.showErrorMessage(getString(errorMessageId))
                            },
                        )
                    }
                    .setOnSummit { nickname, nickNameDialog ->
                        viewModel.joinGroup(code, nickname)

                        subscribeObservableGroupResult(
                            onLoading = { errorMessageId ->
                                showLoading(true, getString(errorMessageId))
                            },
                            onSuccess = {
                                nickNameDialog.dismiss()

                                handleSuccessJoinGroup()
                            },
                            onUnknownError = { errorMessage ->
                                showLoading(false)

                                dialog.showErrorMessage(errorMessage)
                            },
                        )
                    }.show()
            }
        }
    }

    private fun subscribeObservableGroupResult(
        onLoading: (Int) -> Unit? = {},
        onSuccess: () -> Unit? = {},
        onUnknownError: (String) -> Unit? = {},
        onValidationNickname: (Int) -> Unit? = {},
        onValidationAccessCode: (Int) -> Unit? = {},
    ) {
        viewModel.groupResult.collectWithLifecycle(viewLifecycleOwner) { groupResultType ->
            when (groupResultType) {
                is GroupResultType.LOADING -> {
                    onLoading(groupResultType.message)
                }

                is GroupResultType.SUCCESS -> {
                    onSuccess()
                }

                is GroupResultType.UnknownError -> {
                    onUnknownError(groupResultType.message)
                }

                is GroupResultType.ValidationAccessCode -> {
                    onValidationAccessCode(groupResultType.message)
                }

                is GroupResultType.ValidationNickname -> {
                    onValidationNickname(groupResultType.message)
                }
            }
        }
    }

    private fun handleSuccessJoinGroup(nickname: String = viewModel.nickName) {
        WelcomeJoinDialog(requireContext(), nickname).apply {
            setOnDismissListener {
                moveHomeActivity()
            }
        }.show()

        guestListDialog.dismiss()
    }

    private fun moveHomeActivity() {
        val groupId = viewModel.groupId.toLong()
        when (activity) {
            is GroupActivity -> {
                HomeActivity.startActivity(binding.root.context, groupId = groupId)
                requireActivity().finish()
            }

            is HomeActivity -> {
                findNavController().navigate(R.id.action_groupJoinFragment_to_homeRankFragment)
                homeViewModel.groupId = groupId
                homeViewModel.gameId = null
            }
        }
    }

    private fun subscribeObservables() {
        with(viewModel) {
            groupItem.filterNotNull().collectWithLifecycle(viewLifecycleOwner) {
                binding.groupAdminView.setGroupItemDetailTitle(it.groupDetail.ownerNickname)
                binding.groupMemberView.setGroupItemDetailTitle("${it.groupDetail.memberCount}명")
                binding.ivGroupJoinBg.loadImage(it.groupDetail.profileImageUrl)
            }
            guestList.observe(viewLifecycleOwner) {
                if (it != null) {
                    guestListDialog.guestListAdapter.submitList(it)
                    guestListDialog.show()
                }
            }
        }
    }
}
