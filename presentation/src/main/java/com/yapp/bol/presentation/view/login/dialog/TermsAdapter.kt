package com.yapp.bol.presentation.view.login.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.domain.model.TermsItem
import com.yapp.bol.presentation.databinding.RvTermsItemBinding

class TermsAdapter(
    private val onClickLike: (Int, Boolean) -> Unit,
) : ListAdapter<TermsItem, TermsAdapter.TermsViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermsViewHolder {
        val binding =
            RvTermsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TermsViewHolder(binding, onClickLike)
    }

    override fun onBindViewHolder(holder: TermsViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    class TermsViewHolder(
        private val binding: RvTermsItemBinding,
        private val onClickLike: (Int, Boolean) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TermsItem, position: Int) {
            binding.tvServiceTitle.text = setTitle(item.title, item.isRequired)
            binding.cbService.isChecked = item.isChecked
            binding.tvServiceDetail.setOnClickListener {
                // 웹뷰 띄우기
            }
            binding.cbService.setOnClickListener {
                onClickLike(position, binding.cbService.isChecked)
            }
        }

        private fun setTitle(title: String, isRequired: Boolean): String {
            val require = if (isRequired) "필수" else "선택"
            return "(${require}) $title"
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<TermsItem>() {
            override fun areItemsTheSame(oldItem: TermsItem, newItem: TermsItem): Boolean {
                return oldItem.code == newItem.code
            }

            override fun areContentsTheSame(oldItem: TermsItem, newItem: TermsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
