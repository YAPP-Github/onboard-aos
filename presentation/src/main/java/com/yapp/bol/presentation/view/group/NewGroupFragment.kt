package com.yapp.bol.presentation.view.group

import com.yapp.bol.presentation.utils.KeyboardVisibilityUtils
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yapp.bol.domain.model.NewGroupItem
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentNewGroupBinding
import com.yapp.bol.presentation.utils.Constant
import com.yapp.bol.presentation.utils.Converter
import com.yapp.bol.presentation.utils.GalleryManager
import com.yapp.bol.presentation.utils.loadRoundImage
import com.yapp.bol.presentation.view.group.dialog.ImageSettingDialog
import com.yapp.bol.presentation.view.group.dialog.ProfileSettingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewGroupFragment : BaseFragment<FragmentNewGroupBinding>(R.layout.fragment_new_group) {

    private val newGroupViewModel: NewGroupViewModel by viewModels()

    private val profileSettingDialog by lazy {
        ProfileSettingDialog(
            context = requireContext(),
            userName = newGroupViewModel.userName,
            createGroup = ::createNewGroup,
        )
    }

    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onViewCreatedAction() {

        keyboardVisibilityUtils = KeyboardVisibilityUtils(
            window = requireActivity().window,
            onShowKeyboard = ::moveScroll,
        )

        setTextChangeListener()
        setClickListener()
        setViewModelObserve()
    }

    private fun setTextChangeListener() = with(binding) {
        etGroupName.doOnTextChanged { text, start, _, count ->
            tvGroupNameCount.text = Converter.convertLengthToString(NAVE_MAX_LENGTH, start + count)
            newGroupViewModel.updateGroupInfo(
                NewGroupViewModel.NEW_GROUP_NAME,
                text?.toString() ?: Constant.EMPTY_STRING
            )
        }

        etGroupDescription.doOnTextChanged { text, start, _, count ->
            tvGroupDescriptionCount.text =
                Converter.convertLengthToString(DESCRIPTION_MAX_LENGTH, start + count)
            newGroupViewModel.updateGroupInfo(
                NewGroupViewModel.NEW_GROUP_DESCRIPTION,
                text?.toString() ?: Constant.EMPTY_STRING
            )
        }

        etGroupOrganization.doOnTextChanged { text, start, _, count ->
            tvGroupOrganizationCount.text =
                Converter.convertLengthToString(ORGANIZATION_MAX_LENGTH, start + count)
            newGroupViewModel.updateGroupInfo(
                NewGroupViewModel.NEW_GROUP_ORGANIZATION,
                text?.toString() ?: Constant.EMPTY_STRING
            )
        }
    }

    private fun moveScroll(keyboardHeight: Int) {
        binding.svScroll.run { smoothScrollTo(scrollX, keyboardHeight / 2) }
    }

    private fun setClickListener() {
        val galleryManager = GalleryManager(
            context = requireActivity() as AppCompatActivity,
            imageView = binding.ivImage,
            uploadImageFile = { file -> newGroupViewModel.updateImageFile(file) }
        )

        binding.ivImage.setOnClickListener {
            generateImageSettingDialog { galleryManager.checkedGalleryAccess() }
        }

        binding.btnCreateGroup.setOnClickListener {
            profileSettingDialog.show()
        }

        binding.ibBackButton.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun setViewModelObserve() = with(newGroupViewModel) {
        groupName.observe(viewLifecycleOwner) {
            binding.btnCreateGroup.isEnabled = isCompleteButtonActivation
        }

        groupDescription.observe(viewLifecycleOwner) {
            binding.btnCreateGroup.isEnabled = isCompleteButtonActivation
        }

        successGroupDate.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            moveNewGroupComplete(it)
        }

        groupRandomImage.observe(viewLifecycleOwner) {
            binding.ivImage.loadRoundImage(it, 8)
        }
    }

    private fun createNewGroup(nickName: String) {
        generateProgressBar()
        newGroupViewModel.createNewGroup(nickName)
    }

    private fun moveNewGroupComplete(newGroupItem: NewGroupItem) {
        stopProgressBar()
        val bundle = Bundle().apply {
            putSerializable(NEW_GROUP, newGroupItem)
        }
        findNavController().apply {
            navigate(R.id.action_newGroupFragment_to_newGroupCompleteFragment, bundle)
        }
    }

    private fun generateImageSettingDialog(checkedGalleryAccess: () -> Unit) {
        val dialog = ImageSettingDialog(requireContext(), checkedGalleryAccess, newGroupViewModel::getRandomImage)
        dialog.show()
    }

    private fun generateProgressBar() {
        binding.loadingBackground.visibility = View.VISIBLE
        binding.tvLoadingText.visibility = View.VISIBLE
        binding.lavLoading.visibility = View.VISIBLE
    }

    private fun stopProgressBar() {
        binding.tvLoadingText.visibility = View.GONE
        binding.lavLoading.visibility = View.GONE
    }

    override fun onDestroyView() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroyView()
    }

    companion object {
        const val NAVE_MAX_LENGTH = 14
        const val DESCRIPTION_MAX_LENGTH = 72
        const val ORGANIZATION_MAX_LENGTH = 15
        const val NEW_GROUP = "new group"
    }
}
