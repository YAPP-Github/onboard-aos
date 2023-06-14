package com.yapp.bol.presentation.view.group.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ViewGroupListErrorBinding

class GroupListLoadStateViewHolder(
    private val binding: ViewGroupListErrorBinding,
    retry: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        binding.tvError1.isVisible = loadState is LoadState.Error
        binding.tvError2.isVisible = loadState is LoadState.Error
        binding.btnRetry.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): GroupListLoadStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ViewGroupListErrorBinding.inflate(inflater, parent, false)
            return GroupListLoadStateViewHolder(binding, retry)
        }
    }
}
