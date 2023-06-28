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
        binding.sflLoading.isVisible = loadState is LoadState.Loading
        binding.llError.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): GroupListLoadStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.view_group_list_error, parent, false)
            val binding = ViewGroupListErrorBinding.bind(view)
            return GroupListLoadStateViewHolder(binding, retry)
        }
    }
}
