package com.yapp.bol.presentation.view.group.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yapp.bol.domain.model.GroupItem
import com.yapp.bol.presentation.databinding.ItemGroupListBinding

class GroupListViewHolder(private val binding: ItemGroupListBinding) : RecyclerView.ViewHolder(binding.root) {
    private var groupItem: GroupItem? = null
    private val glide by lazy { Glide.with(binding.root) }

    init {
        binding.root.setOnClickListener {
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
