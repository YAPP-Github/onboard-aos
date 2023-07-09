package com.yapp.bol.presentation.view.group.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ItemGroupListNotFoundBinding

class GroupListNotFoundViewHolder(
    private val binding: ItemGroupListNotFoundBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnMakeGroup.setOnClickListener {
            // TODO : 그룹 생성 fragment 로 전환 코드 필요
        }
    }

    fun bind(textInput: String) {
        binding.tvGuide.text = binding.root.context.getString(R.string.group_search_not_found).format(textInput)
    }

    companion object {
        fun create(parent: ViewGroup): GroupListNotFoundViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemGroupListNotFoundBinding.inflate(inflater, parent, false)
            return GroupListNotFoundViewHolder(binding)
        }
    }
}
