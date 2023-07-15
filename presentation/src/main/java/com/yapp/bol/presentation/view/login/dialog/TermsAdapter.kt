package com.yapp.bol.presentation.view.login.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.domain.model.TermsItem
import com.yapp.bol.presentation.databinding.RvTermsItemBinding

class TermsAdapter(
    private val onClickItemListener: OnClickItemListener,
) : ListAdapter<TermsItem, TermsAdapter.TermsViewHolder>(diff) {

    interface OnClickItemListener {
        fun onClickLike(position: Int, isChecked: Boolean)
        fun onClickTermsDetail(url: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermsViewHolder {
        val binding =
            RvTermsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TermsViewHolder(binding, onClickItemListener)
    }

    override fun onBindViewHolder(holder: TermsViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    class TermsViewHolder(
        private val binding: RvTermsItemBinding,
        private val onClickItemListener: OnClickItemListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TermsItem, position: Int) {
            binding.tvServiceTitle.text = setTitle(item.title, item.isRequired)
            binding.cbService.isChecked = item.isChecked
            binding.tvServiceDetail.setOnClickListener {
                // 웹뷰 띄우기
            }
            binding.cbService.setOnClickListener {
                onClickItemListener.onClickLike(position, binding.cbService.isChecked)
            }

            binding.root.setOnClickListener {
                binding.cbService.isChecked = item.isChecked.not()
                onClickItemListener.onClickLike(position, binding.cbService.isChecked)
            }

            binding.tvServiceDetail.setOnClickListener {
                onClickItemListener.onClickTermsDetail(item.url)
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
