package com.yapp.bol.presentation.view.group.search

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.yapp.bol.domain.model.GroupItem
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ItemGroupListBinding
import com.yapp.bol.presentation.utils.colorTransitionAnimator as colorAnim
import com.yapp.bol.presentation.utils.textColorSmoothTransitionAnimator as textColorAnim

class GroupListViewHolder(private val binding: ItemGroupListBinding) : RecyclerView.ViewHolder(binding.root) {
    private val glide by lazy { Glide.with(binding.root) }

    init {
        binding.root.setOnClickListener {
            binding.onClickAnimation()
            // TODO : 다음 fragment 로 전환 코드 필요
        }
    }

    fun bind(groupItem: GroupItem) {
        binding.apply {
            tvGroupDescription.text = groupItem.description
            tvGroupName.text = groupItem.name
            tvGroupOrganization.text = groupItem.organization
            "${groupItem.memberCount}명".also { tvGroupSize.text = it }
            ivGroupImage.setImageWithGlide(groupItem.profileImageUrl)
        }
    }

    // TODO : color 리소스로 변경 필요
    // TODO : design system button으로 교체 필요
    private fun ItemGroupListBinding.onClickAnimation() {
        val animDuration = 150L

        root.colorAnim(
            startColor = Color.parseColor("#242424"),
            endColor = Color.parseColor("#FFFFFF"),
            durationMS = animDuration,
        ).start()

        tvGroupName.textColorAnim(
            startColor = Color.parseColor("#FBFBFB"),
            endColor = Color.parseColor("#171717"),
            durationMS = animDuration,
        ).start()

        tvGroupDescription.textColorAnim(
            startColor = Color.parseColor("#FBFBFB"),
            endColor = Color.parseColor("#171717"),
            durationMS = animDuration,
        ).start()

        tvGroupSize.textColorAnim(
            startColor = Color.parseColor("#F5F4F3"),
            endColor = Color.parseColor("#A5A5A5"),
            durationMS = animDuration,
        ).start()

        tvGroupOrganization.textColorAnim(
            startColor = Color.parseColor("#F5F4F3"),
            endColor = Color.parseColor("#A5A5A5"),
            durationMS = animDuration,
        ).start()

        viewSeparator.colorAnim(
            startColor = Color.parseColor("#F5F4F3"),
            endColor = Color.parseColor("#C1C1C1"),
            durationMS = animDuration,
        ).start()
    }

    // TODO : merge 후 util 함수로 교체
    private fun ImageView.setImageWithGlide(uri: String) {
        glide
            .load(uri)
            .transform(CenterCrop(), RoundedCorners(resources.getDimension(R.dimen.group_search_item_image_radius).toInt()))
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
