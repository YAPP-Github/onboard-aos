package com.yapp.bol.presentation.view.group.search

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.yapp.bol.domain.model.GroupSearchItem

class GroupListAdapter : PagingDataAdapter<GroupSearchItem, GroupListViewHolder>(GROUP_LIST_COMPARATOR) {
    override fun onBindViewHolder(holder: GroupListViewHolder, position: Int) {
        val groupItem = getItem(position)
        holder.bind(groupItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupListViewHolder {
        return GroupListViewHolder.create(parent)
    }

    companion object {
        private val GROUP_LIST_COMPARATOR = object : DiffUtil.ItemCallback<GroupSearchItem>() {
            override fun areItemsTheSame(oldItem: GroupSearchItem, newItem: GroupSearchItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GroupSearchItem, newItem: GroupSearchItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}
