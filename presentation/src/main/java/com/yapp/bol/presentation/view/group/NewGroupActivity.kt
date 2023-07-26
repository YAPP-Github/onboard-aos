package com.yapp.bol.presentation.view.group

import KeyboardVisibilityUtils
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import com.yapp.bol.domain.model.NewGroupItem
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ActivityNewGroupBinding
import com.yapp.bol.presentation.utils.Constant.EMPTY_STRING
import com.yapp.bol.presentation.utils.Converter.convertLengthToString
import com.yapp.bol.presentation.utils.GalleryManager
import com.yapp.bol.presentation.utils.convertPxToDp
import com.yapp.bol.presentation.utils.loadImage
import com.yapp.bol.presentation.view.group.NewGroupViewModel.Companion.NEW_GROUP_DESCRIPTION
import com.yapp.bol.presentation.view.group.NewGroupViewModel.Companion.NEW_GROUP_NAME
import com.yapp.bol.presentation.view.group.NewGroupViewModel.Companion.NEW_GROUP_ORGANIZATION
import com.yapp.bol.presentation.view.group.dialog.ImageSettingDialog
import com.yapp.bol.presentation.view.group.dialog.NewGroupCompleteDialog
import com.yapp.bol.presentation.view.group.dialog.ProfileSettingDialog
import com.yapp.bol.presentation.view.match.MatchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewGroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewGroupBinding
    private val newGroupViewModel: NewGroupViewModel by viewModels()

    private val profileSettingDialog by lazy {
        ProfileSettingDialog(
            context = this,
            createGroup = ::createNewGroup,
        )
    }

    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        keyboardVisibilityUtils = KeyboardVisibilityUtils(
            window = window,
            onShowKeyboard = ::moveScroll,
        )

        setTextChangeListener()
        setClickListener()
        setViewModelObserve()
    }

    private fun setTextChangeListener() {
        binding.etGroupName.doOnTextChanged { text, start, _, count ->
            binding.tvGroupNameCount.text = convertLengthToString(NAVE_MAX_LENGTH, start + count)
            newGroupViewModel.updateGroupInfo(NEW_GROUP_NAME, text?.toString() ?: EMPTY_STRING)
        }

        binding.etGroupDescription.doOnTextChanged { text, start, _, count ->
            binding.tvGroupDescriptionCount.text = convertLengthToString(DESCRIPTION_MAX_LENGTH, start + count)
            newGroupViewModel.updateGroupInfo(NEW_GROUP_DESCRIPTION, text?.toString() ?: EMPTY_STRING)
        }

        binding.etGroupOrganization.doOnTextChanged { text, start, _, count ->
            binding.tvGroupOrganizationCount.text = convertLengthToString(ORGANIZATION_MAX_LENGTH, start + count)
            newGroupViewModel.updateGroupInfo(NEW_GROUP_ORGANIZATION, text?.toString() ?: EMPTY_STRING)
        }
    }

    private fun setClickListener() {
        val galleryManager = GalleryManager(
            context = this,
            imageView = binding.ivImage,
            uploadImageFile = { file -> newGroupViewModel.updateImageFile(file) }
        )

        binding.ivImage.setOnClickListener {
            generateImageSettingDialog { galleryManager.checkedGalleryAccess() }
        }

        binding.btnCreateGroup.setOnClickListener {
            profileSettingDialog.show()
        }
    }

    private fun setViewModelObserve() {
        newGroupViewModel.groupName.observe(this) {
            binding.btnCreateGroup.isEnabled = newGroupViewModel.isCompleteButtonActivation
        }

        newGroupViewModel.groupDescription.observe(this) {
            binding.btnCreateGroup.isEnabled = newGroupViewModel.isCompleteButtonActivation
        }

        newGroupViewModel.successGroupDate.observe(this) {
            if (it == null) return@observe
            generateNewGroupCompleteDialog(it)
        }

        newGroupViewModel.groupRandomImage.observe(this) {
            binding.ivImage.loadImage(it)
        }
    }

    private fun createNewGroup(nickName: String) {
        generateProgressBar()
        newGroupViewModel.createNewGroup(nickName)
    }

    private fun generateNewGroupCompleteDialog(newGroupItem: NewGroupItem) {
        stopProgressBar()
        NewGroupCompleteDialog(
            context = this,
            newGroup = newGroupItem,
            moveHome = { groupId -> moveMatchActivity(groupId) }
        ).show()
    }

    private fun moveScroll(keyboardHeight: Int) {
        binding.svScroll.run { smoothScrollTo(scrollX, keyboardHeight / 2) }
    }

    private fun generateImageSettingDialog(checkedGalleryAccess: () -> Unit) {
        val dialog = ImageSettingDialog(this, checkedGalleryAccess, newGroupViewModel::getRandomImage)
        dialog.show()
    }

    private fun generateProgressBar() {
        binding.loadingBackground.visibility = View.VISIBLE
        binding.tvLoadingText.visibility = View.VISIBLE
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun stopProgressBar() {
        binding.tvLoadingText.visibility = View.GONE
        binding.pbLoading.visibility = View.GONE
    }

    private fun getScreenHeight(): Int {
        val density = resources.displayMetrics.density
        val display = this.applicationContext?.resources?.displayMetrics
        val dpHeight = (display?.heightPixels ?: 0) / density

        return dpHeight.toInt()
    }

    private fun moveMatchActivity(groupId: Int) {
        val intent = Intent(this, MatchActivity::class.java)
        intent.putExtra(GROUP_ID, groupId)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroy()
    }

    companion object {
        const val NAVE_MAX_LENGTH = 14
        const val DESCRIPTION_MAX_LENGTH = 72
        const val ORGANIZATION_MAX_LENGTH = 15
        const val BASE_DEVICE_HEIGHT = 760
        const val BASE_MARGIN_TOP = 55
        const val BASE_MARGIN_HORIZONTAL = 18
        const val GROUP_ID = "Group Id"
    }
}
