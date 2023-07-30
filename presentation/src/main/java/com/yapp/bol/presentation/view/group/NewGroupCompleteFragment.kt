package com.yapp.bol.presentation.view.group

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import com.yapp.bol.domain.model.NewGroupItem
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentNewGroupCompleteBinding
import com.yapp.bol.presentation.utils.copyToClipboard
import com.yapp.bol.presentation.utils.loadImage
import com.yapp.bol.presentation.view.group.NewGroupFragment.Companion.NEW_GROUP
import com.yapp.bol.presentation.view.home.HomeActivity


class NewGroupCompleteFragment : BaseFragment<FragmentNewGroupCompleteBinding>(R.layout.fragment_new_group_complete) {

    private val newGroup by lazy { arguments?.getSerializable(NEW_GROUP) as NewGroupItem }

    override fun onViewCreatedAction() {
        binding.setText(newGroup)
        binding.setImage(newGroup)
        binding.setOnClick(newGroup)
        setOnBackPressed()
    }

    private fun FragmentNewGroupCompleteBinding.setText(newGroup: NewGroupItem) {
        this.tvGroupTitle.text = newGroup.name
        this.tvGroupDescription.text = newGroup.description
        this.tvGroupAccessCodeValue.text = newGroup.accessCode
        this.tvGroupOrganizationValue.text = newGroup.organization
        this.tvGroupOwnerValue.text = newGroup.owner
    }

    private fun FragmentNewGroupCompleteBinding.setImage(newGroup: NewGroupItem) {
        this.ivGroupImage.loadImage(newGroup.imageUrl,0)
    }

    private fun FragmentNewGroupCompleteBinding.setOnClick(newGroup: NewGroupItem) {
        this.btnGroupComplete.setOnClickListener {
            moveHomeActivity()
        }

        this.tvGroupAccessCodeValue.setOnClickListener {
            newGroup.accessCode.copyToClipboard(requireContext())
        }
        this.ibCopyBtn.setOnClickListener {
            newGroup.accessCode.copyToClipboard(requireContext())
        }
    }

    private fun setOnBackPressed() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { moveHomeActivity() }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun moveHomeActivity() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        intent.putExtra(HomeActivity.HOME_GROUP_ID_KEY, newGroup.id.toLong())
        startActivity(intent)
        requireActivity().finish()
    }

}
