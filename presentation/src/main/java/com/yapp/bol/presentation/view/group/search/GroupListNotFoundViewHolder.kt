package com.yapp.bol.presentation.view.group.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ItemGroupListNotFoundBinding

class GroupListNotFoundViewHolder(
    private val binding: ItemGroupListNotFoundBinding,
    private val changeButtonColor: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(textInput: String) {
        changeButtonColor.invoke()
        binding.tvGuide.text = binding.root.context.getString(R.string.group_search_not_found).format(textInput)
    }

    companion object {
        fun create(
            parent: ViewGroup,
            changeButtonColor: () -> Unit,
        ): GroupListNotFoundViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemGroupListNotFoundBinding.inflate(inflater, parent, false)
            return GroupListNotFoundViewHolder(binding, changeButtonColor)
        }
    }
}
