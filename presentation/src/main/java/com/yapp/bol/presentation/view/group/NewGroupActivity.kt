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
import com.yapp.bol.presentation.view.group.dialog.ImageSettingDialog
import com.yapp.bol.presentation.view.group.dialog.ProfileSettingDialog
import com.yapp.bol.presentation.view.group.NewGroupViewModel.Companion.NEW_GROUP_DESCRIPTION
import com.yapp.bol.presentation.view.group.NewGroupViewModel.Companion.NEW_GROUP_NAME
import com.yapp.bol.presentation.view.group.NewGroupViewModel.Companion.NEW_GROUP_ORGANIZATION
import com.yapp.bol.presentation.view.login.KakaoTestActivity.Companion.ACCESS_TOKEN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewGroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewGroupBinding
    private val newGroupViewModel: NewGroupViewModel by viewModels()
    private val accessToken by lazy {
        intent.getStringExtra(ACCESS_TOKEN) ?: EMPTY_STRING
    }

    private val profileSettingDialog by lazy {
        ProfileSettingDialog(
            context = this,
            createGroup = ::createNewGroup,
        )
    }

    private val keyboardVisibilityUtils by lazy {
        KeyboardVisibilityUtils(
            window = window,
            onShowKeyboard = ::moveScroll,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        keyboardVisibilityUtils
        setTextChangeListener()
        setCreateGroupButton()
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
            moveNewGroupComplete(it)
        }
    }

    private fun createNewGroup(nickName: String) {
        generateProgressBar()
        newGroupViewModel.createNewGroup(accessToken, nickName)
    }

    private fun moveNewGroupComplete(newGroupItem: NewGroupItem) {
        val intent = Intent(this, NewGroupCompleteActivity::class.java)
        intent.putExtra(ACCESS_CODE_KEY, newGroupItem)
        startActivity(intent)
        finish()
    }

    private fun moveScroll(keyboardHeight: Int) {
        binding.svScroll.run { smoothScrollTo(scrollX, keyboardHeight / 2) }
    }

    private fun generateImageSettingDialog(checkedGalleryAccess: () -> Unit) {
        val dialog = ImageSettingDialog(this, checkedGalleryAccess)
        dialog.show()
    }

    private fun generateProgressBar() {
        binding.loadingBackground.visibility = View.VISIBLE
        binding.tvLoadingText.visibility = View.VISIBLE
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun setCreateGroupButton() {
        val params = ConstraintLayout.LayoutParams(0, convertPxToDp(52))
        params.setMargins(
            convertPxToDp(BASE_MARGIN_HORIZONTAL),
            convertPxToDp(BASE_MARGIN_TOP + (getScreenHeight() - BASE_DEVICE_HEIGHT)),
            convertPxToDp(BASE_MARGIN_HORIZONTAL),
            0
        )
        params.topToBottom = R.id.et_group_organization
        params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID

        binding.btnCreateGroup.layoutParams = params
    }

    private fun getScreenHeight(): Int {
        val density = resources.displayMetrics.density
        val display = this.applicationContext?.resources?.displayMetrics
        val dpHeight = (display?.heightPixels ?: 0) / density

        return dpHeight.toInt()
    }

    override fun onDestroy() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroy()
    }

    companion object {
        const val ACCESS_CODE_KEY = "Access Code"
        const val NAVE_MAX_LENGTH = 14
        const val DESCRIPTION_MAX_LENGTH = 72
        const val ORGANIZATION_MAX_LENGTH = 15
        const val BASE_DEVICE_HEIGHT = 760
        const val BASE_MARGIN_TOP = 55
        const val BASE_MARGIN_HORIZONTAL = 18
    }
}
