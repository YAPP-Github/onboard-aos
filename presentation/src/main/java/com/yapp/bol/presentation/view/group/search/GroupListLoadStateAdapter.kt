package com.yapp.bol.presentation.view.group.search

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class GroupListLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<GroupListLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: GroupListLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): GroupListLoadStateViewHolder {
        return GroupListLoadStateViewHolder.create(parent, retry)
    }
}
