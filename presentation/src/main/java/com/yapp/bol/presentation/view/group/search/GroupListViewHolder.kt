package com.yapp.bol.presentation.view.group.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.databinding.ItemGroupListBinding

class GroupListViewHolder(private val binding: ItemGroupListBinding) : RecyclerView.ViewHolder(binding.root) {
    private var groupItem: String? = null

    init {
        binding.root.setOnClickListener {
            // TODO : 다음 fragment 로 전환 코드 필요
        }
    }

    fun bind(groupItem: String?) {
        if (groupItem == null) {
            // TODO ERROR
            // binding.ivGroupImage.setImageDrawable()
        } else {
            showGroupData(groupItem)
        }
    }

    private fun showGroupData(groupItem: String) {
        this.groupItem = groupItem

        binding.apply {
            tvGroupDescription.text = ""
            tvGroupName.text = ""
            tvGroupOrganization.text = ""
            tvGroupSize.text = ""
            // ivGroupImage.setImageDrawable()
        }
    }

    companion object {
        fun create(parent: ViewGroup): GroupListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemGroupListBinding.inflate(inflater, parent, false)
            return GroupListViewHolder(binding)
        }
    }
}
