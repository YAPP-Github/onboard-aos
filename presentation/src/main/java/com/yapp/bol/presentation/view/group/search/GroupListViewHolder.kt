package com.yapp.bol.presentation.view.group.search

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yapp.bol.domain.model.GroupItem
import com.yapp.bol.presentation.databinding.ItemGroupListBinding
import com.yapp.bol.presentation.utils.colorAnimator
import com.yapp.bol.presentation.utils.textColorAnimator

class GroupListViewHolder(private val binding: ItemGroupListBinding) : RecyclerView.ViewHolder(binding.root) {
    private var groupItem: GroupItem? = null
    private val glide by lazy { Glide.with(binding.root) }

    init {
        binding.root.setOnClickListener {
            binding.onClickAnimation()
            // TODO : 다음 fragment 로 전환 코드 필요
        }
    }

    fun bind(groupItem: GroupItem?) {
        if (groupItem == null) {
            // TODO ERROR
        } else {
            showGroupData(groupItem)
        }
    }

    private fun ItemGroupListBinding.onClickAnimation() {
        val animDuration = 400L

        root.colorAnimator(
            middleColor = Color.parseColor("#242424"),
            endColor = Color.parseColor("#FFFFFF"),
            durationMS = animDuration,
        ).start()

        tvGroupName.textColorAnimator(
            middleColor = Color.parseColor("#FBFBFB"),
            endColor = Color.parseColor("#171717"),
            durationMS = animDuration,
        ).start()

        tvGroupDescription.textColorAnimator(
            middleColor = Color.parseColor("#FBFBFB"),
            endColor = Color.parseColor("#171717"),
            durationMS = animDuration,
        ).start()

        tvGroupSize.textColorAnimator(
            middleColor = Color.parseColor("#FBFBFB"),
            endColor = Color.parseColor("#171717"),
            durationMS = animDuration,
        ).start()

        tvGroupOrganization.textColorAnimator(
            middleColor = Color.parseColor("#FBFBFB"),
            endColor = Color.parseColor("#171717"),
            durationMS = animDuration,
        ).start()

        viewSeparator.colorAnimator(
            middleColor = Color.parseColor("#242424"),
            endColor = Color.parseColor("#FFFFFF"),
            durationMS = animDuration,
        ).start()
    }

    private fun showGroupData(groupItem: GroupItem) {
        this.groupItem = groupItem

        binding.apply {
            tvGroupDescription.text = groupItem.description
            tvGroupName.text = groupItem.name
            tvGroupOrganization.text = groupItem.organization
            tvGroupSize.text = groupItem.memberCount.toString()
            ivGroupImage.setImageWithGlide(groupItem.profileImageUrl)
        }
    }

    private fun ImageView.setImageWithGlide(uri: String) {
        glide
            .load(uri)
            .into(this)
    }

    companion object {
        fun create(parent: ViewGroup): GroupListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemGroupListBinding.inflate(inflater, parent, false)
            return GroupListViewHolder(binding)
        }
    }
}
